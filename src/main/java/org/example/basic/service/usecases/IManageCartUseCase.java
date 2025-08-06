package org.example.basic.service.usecases;

import java.util.Optional;
import org.example.basic.repository.entity.Cart;
import org.example.basic.service.IInputPort;

public interface IManageCartUseCase extends IInputPort {

    void addProductToCart(Long customerId, Long productId, Long optionId, int quantity);

    Optional<Cart> getCart(Long customerId);
}