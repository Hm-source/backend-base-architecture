package org.example.basic.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.Cart;
import org.example.basic.repository.ports.ICartRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartRepository implements ICartRepositoryPort {

    private final Map<Long, Cart> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Optional<Cart> findByCustomerId(Long customerId) {
        return store.values().stream().filter((each) -> customerId.equals(each.getCustomerId()))
            .findFirst();
    }

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() == null) {
            long generatedId = sequence.incrementAndGet();
            cart.setId(generatedId);
        }
        store.put(cart.getId(), cart);
        return store.get(cart.getId());
    }
}
