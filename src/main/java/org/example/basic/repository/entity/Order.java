package org.example.basic.repository.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.basic.repository.entity.vo.OrderItem;
import org.example.basic.repository.entity.vo.OrderStatus;
import org.example.basic.repository.entity.vo.ShippingInfo;

@Getter
@ToString
@AllArgsConstructor
public class Order {

    @Setter
    private Long id;
    private final long customerId;
    private final List<OrderItem> orderItems;
    private long originalPrice;
    private long discountedPrice;
    private final ShippingInfo shippingInfo;
    private OrderStatus status;
    private final LocalDateTime orderDate;

    // 불변 필드만 초기화해서 객체를 안전하게 만들기 위해
    public Order(Long customerId, List<OrderItem> orderItems, ShippingInfo shippingInfo) {
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.shippingInfo = shippingInfo;
        this.status = OrderStatus.PAYMENT_READY;
        this.orderDate = LocalDateTime.now();
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        this.originalPrice = orderItems.stream().mapToLong(OrderItem::calculatePrice).sum();
        this.discountedPrice = this.originalPrice;
    }
}
