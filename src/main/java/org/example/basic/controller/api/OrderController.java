package org.example.basic.controller.api;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.controller.api.dto.OrderResponseDto;
import org.example.basic.repository.entity.Order;
import org.example.basic.service.usecases.IPurchaseOrderUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor // final로 선언된 필드만 골라서 생성자 주입 코드를 만들어주는 lombok 애너테이션
public class OrderController {

    // Input port iPurchaseOrderUseCase
    private final IPurchaseOrderUseCase orderService;
    private static final Long MOCK_CUSTOMER_ID = 1L;

    // 주문 리스트 조회 API
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<OrderResponseDto>> orderStatus() {
        List<Order> retrieved = orderService.getOrders(MOCK_CUSTOMER_ID);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(retrieved.stream().map(OrderResponseDto::from).toList());
    }

    // 주문 디테일 조회 API
    @GetMapping("/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderResponseDto> orderStatus(@PathVariable Long orderId) {
        Order retrieved = orderService.getOrderById(orderId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(OrderResponseDto.from(retrieved));
    }
}
