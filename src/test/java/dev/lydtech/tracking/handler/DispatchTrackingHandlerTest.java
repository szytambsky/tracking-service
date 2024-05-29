package dev.lydtech.tracking.handler;

import dev.lydtech.dispatch.message.DispatchCompleted;
import dev.lydtech.dispatch.message.DispatchPreparing;
import dev.lydtech.tracking.TestEventData;
import dev.lydtech.tracking.service.TrackingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.*;

class DispatchTrackingHandlerTest {

    private DispatchTrackingHandler handler;
    private TrackingService trackingServiceMock;

    @BeforeEach
    void setUp() {
        trackingServiceMock = mock(TrackingService.class);
        handler = new DispatchTrackingHandler(trackingServiceMock);
    }

    @Test
    void listen_Success() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(randomUUID());
        DispatchCompleted testEvent2 = TestEventData.buildDispatchCompletedEvent(randomUUID());
        handler.listen(testEvent);
        handler.listenOnCompleted(testEvent2);
        verify(trackingServiceMock, times(1)).process(testEvent);
        verify(trackingServiceMock, times(1)).processOnCompleted(testEvent2);
    }

    @Test
    void listen_ServiceThrowsException() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(randomUUID());
        DispatchCompleted testEvent2 = TestEventData.buildDispatchCompletedEvent(randomUUID());
        doThrow(new RuntimeException("Service failure")).when(trackingServiceMock).process(testEvent);
        doThrow(new RuntimeException("Service failure")).when(trackingServiceMock).processOnCompleted(testEvent2);
        handler.listen(testEvent);
        handler.listenOnCompleted(testEvent2);
        verify(trackingServiceMock, times(1)).process(testEvent);
        verify(trackingServiceMock, times(1)).processOnCompleted(testEvent2);
    }
}