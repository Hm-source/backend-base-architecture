package org.example.basic.message.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderPaidEvent {

    private final Long orderId;
}