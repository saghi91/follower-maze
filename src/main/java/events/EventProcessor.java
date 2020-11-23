package events;

import events.factory.EventFactory;
import exceptions.EventException;
import queues.DeadLetterQueueInterface;
import queues.QueueInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EventProcessor implements Runnable {
    private final QueueInterface eventQueue;
    private final DeadLetterQueueInterface deadLetterQueueInterface;
    private final ServerSocket serverSocket;

    public EventProcessor(EventQueue eventQueue, DeadLetterQueueInterface deadLetterQueueInterface, ServerSocket serverSocket) {
        this.eventQueue = eventQueue;
        this.deadLetterQueueInterface = deadLetterQueueInterface;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            Socket eventSocket = serverSocket.accept();
            while (eventSocket != null) {
                process(eventSocket);
                eventSocket = serverSocket.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String rawPayload = reader.readLine();
        while (rawPayload != null) {
            try {
                BaseEvent baseEvent = EventFactory.create(rawPayload);
                eventQueue.add(baseEvent);
                rawPayload = reader.readLine();
            } catch (EventException e) {
                deadLetterQueueInterface.add(rawPayload);
                rawPayload = reader.readLine();
            }
        }
    }
}
