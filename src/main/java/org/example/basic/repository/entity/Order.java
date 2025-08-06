package org.example.basic.repository.entity;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.basic.message.event.OrderPaidEvent;
import org.example.basic.repository.entity.vo.OrderItem;
import org.example.basic.repository.entity.vo.OrderStatus;
import org.example.basic.repository.entity.vo.ShippingInfo;

@Getter
@ToString
@AllArgsConstructor
public class Order extends AggregateRoot {

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

    public void applyCoupon(Coupon coupon) {
        if (coupon == null) {
            return;
        }
        long discountAmount = coupon.calculateDiscount(this.originalPrice);
        long priceAfterDiscount = this.originalPrice - discountAmount;
        this.discountedPrice = Math.round(priceAfterDiscount / 100.0) * 100;
    }

    public void paymentComplete() {
        if (this.status != OrderStatus.PAYMENT_READY) {
            throw new IllegalStateException("결제 대기 상태에서만 결제 완료가 가능합니다.");
        }
        this.status = OrderStatus.PAYMENT_COMPLETE;
        this.addDomainEvent(new OrderPaidEvent(this.id)); // 이벤트 발행 코드
    }

    public void startShipping() {
        if (this.status != OrderStatus.PAYMENT_COMPLETE) {
            throw new IllegalStateException("결제가 완료되어야 배송을 시작할 수 있습니다.");
        }
        this.status = OrderStatus.SHIPPING_READY;
    }
}
