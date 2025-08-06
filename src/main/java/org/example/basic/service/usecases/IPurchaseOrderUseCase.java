package org.example.basic.service.usecases;

import java.util.List;
import org.example.basic.repository.entity.Coupon;
import org.example.basic.repository.entity.Order;
import org.example.basic.repository.entity.vo.PaymentMethod;
import org.example.basic.repository.entity.vo.ShippingInfo;
import org.example.basic.service.IInputPort;

public interface IPurchaseOrderUseCase extends IInputPort {

    Order process(Long customerId, ShippingInfo shippingInfo, PaymentMethod paymentMethod,
        Coupon coupon);

    List<Order> getOrders(Long customerId);

    Order getOrderById(Long orderId);
}
