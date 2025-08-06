package org.example.basic.service.usecases;

import java.util.List;
import org.example.basic.repository.entity.Product;
import org.example.basic.service.IInputPort;

public interface IDisplayProductUseCase extends IInputPort {

    List<Product> getProducts(String keyword);

    Product getProductById(Long productId);
}