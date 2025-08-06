package org.example.basic.controller.api;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.controller.api.dto.ProductResponseDto;
import org.example.basic.repository.entity.Product;
import org.example.basic.service.usecases.IDisplayProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IDisplayProductUseCase productService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<ProductResponseDto>> listProducts() {
        List<Product> retrieved = productService.getProducts(null);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(retrieved.stream().map(ProductResponseDto::from).toList());
    }

    @GetMapping("/{productId}")
    @ResponseBody
    public ResponseEntity<ProductResponseDto> productDetail(@PathVariable Long productId) {
        Product retrieved = productService.getProductById(productId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ProductResponseDto.from(retrieved));
    }
}
