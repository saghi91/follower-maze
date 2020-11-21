package events;

import events.factory.EventFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EventProcessor implements Runnable {
    private final EventQueue eventQueue;
    private final ServerSocket serverSocket;

    public EventProcessor(EventQueue eventQueue, java.net.ServerSocket serverSocket) {
        this.eventQueue = eventQueue;
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
            BaseEvent baseEvent = EventFactory.create(rawPayload);
            eventQueue.add(baseEvent);
            rawPayload = reader.readLine();
        }
    }
}
