package events;

import events.factory.EventFactory;
import queues.DeadLetterQueue;
import queues.EventQueue;
import queues.QueueInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EventProcessor implements Runnable {
    private final QueueInterface<BaseEvent> eventQueue;
    private final QueueInterface<String> deadLetterQueue;
    private final ServerSocket serverSocket;

    public EventProcessor(EventQueue eventQueue, DeadLetterQueue deadLetterQueue,
                          ServerSocket serverSocket) {

        this.eventQueue = eventQueue;
        this.deadLetterQueue = deadLetterQueue;
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
                deadLetterQueue.add(e.getRawPayload());
                rawPayload = reader.readLine();
            }
        }
    }
}
