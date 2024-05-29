package dev.lydtech.tracking.service;

import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.tracking.message.TrackingStatus;
import dev.lydtech.tracking.message.TrackingStatusUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingService {

    private static final String TRACKING_STATUS_TOPIC = "tracking.status";
    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(DispatchPreparing dispatchPreparing) throws ExecutionException, InterruptedException {
        log.info("Received dispatch preparing message : " + dispatchPreparing);
        TrackingStatusUpdated trackingStatusUpdated = TrackingStatusUpdated.builder()
                .orderId(dispatchPreparing.getOrderId())
                .status(TrackingStatus.PREPARING)
                .build();
        kafkaProducer.send(TRACKING_STATUS_TOPIC, trackingStatusUpdated).get();
    }

    public void processOnCompleted(DispatchCompleted dispatchCompleted) throws Exception {
        log.info("Received dispatch completed message : " + dispatchCompleted);
        TrackingStatusUpdated trackingStatusUpdated = TrackingStatusUpdated.builder()
                .orderId(dispatchCompleted.getOrderId())
                .status(TrackingStatus.DISPATCHED)
                .build();
        kafkaProducer.send(TRACKING_STATUS_TOPIC, trackingStatusUpdated).get();
    }
}
