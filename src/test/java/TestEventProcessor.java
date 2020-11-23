import clients.UserRepository;
import events.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import queues.DeadLetterQueueInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestEventProcessor {
    @Mock
    private Socket socket;

    @Mock
    private ServerSocket serverSocket;

    @Mock
    private EventQueue eventQueue;

    private EventProcessor eventProcessor;

    private DeadLetterQueueInterface deadLetterQueue = new DeadLetterEventQueue();

    @Before
    public void setUp() throws IOException {
        when(serverSocket.accept()).thenReturn(socket, null);
        eventProcessor = new EventProcessor(eventQueue, deadLetterQueue, serverSocket);
    }

    @Test
    public void testEventIsPushedToDLQWhenSequenceNumberInvalid() throws IOException {
        //Given
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("FOO|S|1".getBytes()));
        //When
        eventProcessor.run();
        //Then
        assertEquals(1, deadLetterQueue.getAll().size());
        assertEquals("FOO|S|1", deadLetterQueue.poll());
    }

    @Test
    public void testEventIsPushedToDLQWhenEventTypeIsUnknown() throws IOException {
        //Given
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("2|FOO|1|2".getBytes()));
        //When
        eventProcessor.run();
        //Then
        assertEquals(1, deadLetterQueue.getAll().size());
        assertEquals("2|FOO|1|2", deadLetterQueue.poll());
    }

    @Test
    public void testEventIsPushedToDLQWhenUserNotSet() throws IOException {
        //Given
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream("3|S".getBytes()));
        //When
        eventProcessor.run();
        //Then
        assertEquals(1, deadLetterQueue.getAll().size());
        assertEquals("3|S", deadLetterQueue.poll());
    }
}
