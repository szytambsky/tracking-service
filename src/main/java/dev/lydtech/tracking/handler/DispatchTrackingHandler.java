package dev.lydtech.tracking.handler;

import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.tracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@KafkaListener(
        id = "dispatchConsumerTracking",
        topics = "dispatch.tracking",
        groupId = "dispatch.tracking.consumer",
        containerFactory = "kafkaListenerContainerFactory"
)
public class DispatchTrackingHandler {

    @Autowired
    private final TrackingService trackingService;

    @KafkaHandler
    public void listen(DispatchPreparing dispatchPreparing) {
        log.info("Payload received: " + dispatchPreparing);
        try {
            trackingService.process(dispatchPreparing);
        } catch (Exception e) {
            log.error("Processing failure", e); // later we cover dead letter topics
        }
    }

    @KafkaHandler
    public void listenOnCompleted(DispatchCompleted dispatchCompleted) {
        log.info("Payload received: " + dispatchCompleted);
        try {
            trackingService.processOnCompleted(dispatchCompleted);
        } catch (Exception e) {
            log.error("Processing failure", e);
        }
    }
}
