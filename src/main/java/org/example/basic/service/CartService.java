package org.example.basic.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.Cart;
import org.example.basic.repository.entity.Product;
import org.example.basic.repository.ports.ICartRepositoryPort;
import org.example.basic.repository.ports.IProductRepositoryPort;
import org.example.basic.service.usecases.IManageCartUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements IManageCartUseCase {

    private final ICartRepositoryPort cartRepository;
    private final IProductRepositoryPort productRepository;

    @Override
    public void addProductToCart(Long customerId, Long productId, Long optionId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId)
            .orElse(new Cart(customerId));

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.getOptionById(optionId)
            .ifPresentOrElse(
                option -> {
                    cart.addProduct(product, option, quantity);
                    cartRepository.save(cart);
                },
                () -> {
                    throw new IllegalArgumentException("상품 옵션을 찾을 수 없습니다.");
                }
            );
    }

    @Override
    public Optional<Cart> getCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }
}