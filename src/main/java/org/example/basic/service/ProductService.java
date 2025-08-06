package org.example.basic.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.Product;
import org.example.basic.repository.ports.IProductRepositoryPort;
import org.example.basic.service.usecases.IDisplayProductUseCase;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService implements IDisplayProductUseCase {

    private final IProductRepositoryPort productRepository;

    @Override
    public List<Product> getProducts(String keyword) {
        List<Product> products = StringUtils.hasText(keyword)
            ? productRepository.findByKeyword(keyword)
            : productRepository.findAll();

        // 재고가 있는 상품만 필터링
        return products.stream()
            .filter(Product::hasStock)
            .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }
}
