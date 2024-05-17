package dev.lydtech.tracking.handler;

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
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).process(testEvent);
    }

    @Test
    void listen_ServiceThrowsException() throws Exception {
        DispatchPreparing testEvent = TestEventData.buildDispatchPreparingEvent(randomUUID());
        doThrow(new RuntimeException("Service failure")).when(trackingServiceMock).process(testEvent);
        handler.listen(testEvent);
        verify(trackingServiceMock, times(1)).process(testEvent);
    }
}