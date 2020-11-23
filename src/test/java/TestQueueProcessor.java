import clients.UserRepository;
import events.*;
import events.EventException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import queues.DeadLetterQueue;
import queues.EventQueue;
import queues.QueueInterface;
import queues.QueueProcessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestQueueProcessor {
    @Mock
    private EventQueue eventQueue;

    @Mock
    private Follow follow;

    @Mock
    private UserRepository userRepository;

    private QueueProcessor queueProcessor;

    private DeadLetterQueue deadLetterQueue = new DeadLetterQueue();

    @Before
    public void setUp() {
        when(eventQueue.peek()).thenReturn(follow, null);
        queueProcessor = new QueueProcessor(eventQueue, userRepository, deadLetterQueue);
    }

    @Test
    public void testPushToDeadLetterWhenEventExceptionCaught() throws EventException {
        //Given
        when(eventQueue.poll()).thenReturn(follow);
        Mockito.doThrow(new EventException("Event cannot be published!", follow.toString())).when(follow).get(userRepository);
        //When
        queueProcessor.run();
        //Then
        assertEquals(1, deadLetterQueue.getAll().size());
    }
}
