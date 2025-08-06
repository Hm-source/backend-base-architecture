package org.example.basic.controller.page.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.basic.repository.entity.vo.PaymentMethod;
import org.example.basic.repository.entity.vo.ShippingInfo;

@Getter
@Setter
public class ProcessOrderRequestDto {

    private String recipient;
    private String address;
    private String phone;
    private PaymentMethod paymentMethod;

    public ShippingInfo toShippingInfo() {
        return new ShippingInfo(recipient, address, phone);
    }
}