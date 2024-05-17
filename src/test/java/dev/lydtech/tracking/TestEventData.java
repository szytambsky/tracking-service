package dev.lydtech.tracking;


import dev.lydtech.dispatch.message.DispatchPreparing;

import java.util.UUID;

public class TestEventData {

    public static DispatchPreparing buildDispatchPreparingEvent(UUID orderId) {
        return DispatchPreparing.builder()
                .orderId(orderId)
                .build();
    }
}
