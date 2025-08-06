package org.example.basic.repository.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.basic.repository.entity.vo.CartItem;

@Getter
@ToString
public class Cart extends AggregateRoot {

    @Setter
    private Long id;
    private final Long customerId;
    private final List<CartItem> items = new ArrayList<>();

    public Cart(Long customerId) {
        this.customerId = customerId;
    }

    public void addProduct(Product product, ProductOption option, int quantity) {
        if (option.getStock() < quantity) {
            throw new IllegalStateException("상품의 재고가 부족하여 장바구니에 담을 수 없습니다.");
        }

        // 이미 담겨있는 삼품/옵션 조합 확인
        items.stream()
            .filter(items -> items.getProductId().equals(product.getId()) && items.getOptionId()
                .equals(option.getId()))
            .findFirst()
            .ifPresentOrElse(
                items -> items.increaseQuantity(quantity),
                () -> items.add(new CartItem(product, option, quantity))
            );
    }

    public long calculateTotalPrice() {
        return items.stream().mapToLong(CartItem::calculatePrice).sum();
    }

}
