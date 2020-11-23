import clients.User;
import clients.UserRepository;
import events.*;
import exceptions.EventException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import queues.DeadLetterQueueInterface;
import queues.QueueProcessor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestQueueProcessor {
    @Mock
    private EventQueue eventQueue;

    @Mock
    private StatusUpdate statusUpdate;

    @Mock
    private UserRepository userRepository;

    private QueueProcessor queueProcessor;

    private DeadLetterQueueInterface deadLetterQueue = new DeadLetterEventQueue();

    @Before
    public void setUp() {
        when(eventQueue.peek()).thenReturn(statusUpdate, null);
        queueProcessor = new QueueProcessor(eventQueue, userRepository, deadLetterQueue);
    }

    @Test
    public void throwEventIsPushedToDLQWhenEventIsNotPublishedSuccessfully() throws EventException {
        //Given
        when(eventQueue.poll()).thenReturn(statusUpdate);
        Mockito.doThrow(new EventException("event cannot be published!", statusUpdate.toString())).doNothing().when(statusUpdate).get(userRepository);
        //When
        queueProcessor.run();
        //Then
        assertEquals(1, deadLetterQueue.getAll().size());
    }
}
