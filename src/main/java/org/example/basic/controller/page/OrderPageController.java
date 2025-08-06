package org.example.basic.controller.page;


import lombok.RequiredArgsConstructor;
import org.example.basic.controller.page.dto.ProcessOrderRequestDto;
import org.example.basic.repository.entity.Cart;
import org.example.basic.repository.entity.Order;
import org.example.basic.repository.entity.vo.PaymentMethod;
import org.example.basic.service.usecases.IManageCartUseCase;
import org.example.basic.service.usecases.IPurchaseOrderUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderPageController {

    private final IPurchaseOrderUseCase orderService;
    private final IManageCartUseCase cartService;
    private static final Long MOCK_CUSTOMER_ID = 1L;

    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        Cart cart = cartService.getCart(MOCK_CUSTOMER_ID)
            .orElseThrow(() -> new IllegalStateException("장바구니가 비어있습니다."));
        model.addAttribute("cart", cart);
        model.addAttribute("paymentMethods", PaymentMethod.values());
        model.addAttribute("orderRequest", new ProcessOrderRequestDto());
        return "order/checkout";
    }

    @PostMapping("/process")
    public String processOrder(@ModelAttribute ProcessOrderRequestDto request, Model model) {
        try {
            Order order = orderService.process(MOCK_CUSTOMER_ID, request.toShippingInfo(),
                request.getPaymentMethod(), null);
            model.addAttribute("order", order);
            return "order/complete";

        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "order/failure";
        }
    }

    @GetMapping("/{orderId}")
    public String orderStatus(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        return "order/status";
    }
}
