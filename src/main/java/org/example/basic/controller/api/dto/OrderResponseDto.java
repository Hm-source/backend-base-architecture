package org.example.basic.controller.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.Order;
import org.example.basic.repository.entity.vo.OrderStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE) // 생성자는 외부에서 사용할 수 없음
public class OrderResponseDto {

    private final Long id;
    private final Long customerId;
    private final List<OrderItemResponseDto> orderItems;
    private final long originalPrice;
    private final long discountedPrice;
    private final String recipient;
    private final String address;
    private final String phone;
    private final OrderStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime orderDate;


    // 정적 팩토리 메서드
    public static OrderResponseDto from(Order entity) {
        return new OrderResponseDto(
            entity.getId(),
            entity.getCustomerId(),
            entity.getOrderItems().stream().map(OrderItemResponseDto::from).toList(),
            entity.getOriginalPrice(),
            entity.getDiscountedPrice(),
            entity.getShippingInfo().getRecipient(),
            entity.getShippingInfo().getAddress(),
            entity.getShippingInfo().getPhone(),
            entity.getStatus(),
            entity.getOrderDate()
        );
    }
}