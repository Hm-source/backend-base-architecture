package org.example.basic.external;

import org.example.basic.repository.entity.Order;
import org.example.basic.repository.entity.vo.PaymentMethod;

public interface IExternalPaymentApiPort {

    boolean processPayment(Order order, PaymentMethod paymentMethod);
}

