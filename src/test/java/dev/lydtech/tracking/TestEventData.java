package dev.lydtech.tracking;

import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.dispatch.message.DispatchPreparing;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestEventData {

    public static DispatchPreparing buildDispatchPreparingEvent(UUID orderId) {
        return DispatchPreparing.builder()
                .orderId(orderId)
                .build();
    }

    public static DispatchCompleted buildDispatchCompletedEvent(UUID orderId) {
        return DispatchCompleted.builder()
                .orderId(orderId)
                .date(LocalDateTime.now().toString())
                .build();
    }
}
