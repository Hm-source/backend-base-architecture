package org.example.basic.controller.api.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.ProductOption;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOptionResponseDto {

    private final Long id;
    private final String name;
    private final long price;
    private final int stock;

    public static ProductOptionResponseDto from(ProductOption entity) {
        return new ProductOptionResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getPrice(),
            entity.getStock()
        );
    }
}