package org.example.basic.repository.ports;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.IOutputPort;
import org.example.basic.repository.entity.Product;

public interface IProductRepositoryPort extends IOutputPort {

    Optional<Product> findById(Long productId);

    List<Product> findAll();

    List<Product> findByKeyword(String keyword);

    Product save(Product product);
}
