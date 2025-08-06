package org.example.basic.repository.ports;

import java.util.Optional;
import org.example.basic.repository.IOutputPort;
import org.example.basic.repository.entity.Cart;

public interface ICartRepositoryPort extends IOutputPort {

    Optional<Cart> findByCustomerId(Long customerId);

    Cart save(Cart cart);

}
