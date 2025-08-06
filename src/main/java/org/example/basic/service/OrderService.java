package org.example.basic.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.basic.external.IExternalPaymentApiPort;
import org.example.basic.message.IMessagePublisherPort;
import org.example.basic.repository.entity.Cart;
import org.example.basic.repository.entity.Coupon;
import org.example.basic.repository.entity.Order;
import org.example.basic.repository.entity.Product;
import org.example.basic.repository.entity.vo.OrderItem;
import org.example.basic.repository.entity.vo.PaymentMethod;
import org.example.basic.repository.entity.vo.ShippingInfo;
import org.example.basic.repository.ports.ICartRepositoryPort;
import org.example.basic.repository.ports.IOrderRepositoryPort;
import org.example.basic.repository.ports.IProductRepositoryPort;
import org.example.basic.service.usecases.IPurchaseOrderUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements IPurchaseOrderUseCase {

    // Output Port
    private final IProductRepositoryPort productRepository;
    private final IOrderRepositoryPort orderRepository;
    private final ICartRepositoryPort cartRepository;

    private final IExternalPaymentApiPort paymentApi;
    private final IMessagePublisherPort eventPublisher;

    @Override
    public Order process(Long customerId, ShippingInfo shippingInfo, PaymentMethod paymentMethod,
        Coupon coupon) {
        Cart cart = cartRepository.findByCustomerId(customerId).orElseThrow();
        List<OrderItem> orderItems = cart.getItems().stream()
            .map(item -> new OrderItem(
                item.getProductId(),
                item.getOptionId(),
                item.getProductName(),
                item.getOptionName(),
                item.getQuantity(),
                item.getPrice())
            )
            .collect(Collectors.toList());

        for (OrderItem orderItem : orderItems) {
            Product product = productRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "상품을 찾을 수 없습니다. ID: " + orderItem.getProductId()));
            if (!product.isAvailable(orderItem.getOptionId(), orderItem.getQuantity())) {
                throw new IllegalStateException("상품의 재고가 부족합니다: " + product.getName());
            }
        }

        Order order = new Order(customerId, orderItems, shippingInfo);
        order.applyCoupon(coupon);
        orderRepository.save(order);

        boolean paymentSuccess = paymentApi.processPayment(order, paymentMethod);

        if (paymentSuccess) {
            orderItems.forEach(line -> {
                Product product = productRepository.findById(line.getProductId()).get();
                product.decreaseStock(line.getOptionId(), line.getQuantity());
                productRepository.save(product);
            });

            order.paymentComplete();
            Order savedOrder = orderRepository.save(order);
            savedOrder.getDomainEvents().forEach(eventPublisher::publish);
            savedOrder.clearDomainEvents();
        }

        // 주문 완료 후 장바구니 비우기
        cartRepository.findByCustomerId(customerId)
            .ifPresent(completedCart -> {
                completedCart.getItems().clear();
                cartRepository.save(completedCart);
            });
        return order;
    }

    @Override
    public List<Order> getOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }
}
