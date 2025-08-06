package org.example.basic.controller.api.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.vo.OrderItem;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderItemResponseDto {

    private final Long optionId;
    private final String optionName;
    private final int quantity;
    private final long price;

    public static OrderItemResponseDto from(OrderItem entity) {
        return new OrderItemResponseDto(
            entity.getOptionId(),
            entity.getOptionName(),
            entity.getQuantity(),
            entity.getPrice()
        );
    }
}