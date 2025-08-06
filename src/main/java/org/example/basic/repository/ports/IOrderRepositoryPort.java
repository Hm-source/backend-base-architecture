package org.example.basic.repository.ports;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.IOutputPort;
import org.example.basic.repository.entity.Order;

public interface IOrderRepositoryPort extends IOutputPort {

    Optional<Order> findById(Long orderId);

    List<Order> findByCustomerId(Long customerId);

    Order save(Order order);


}
