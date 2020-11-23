import clients.ClientProcessor;
import clients.UserRepository;
import events.DeadLetterEventQueue;
import events.EventProcessor;
import events.EventQueue;
import queues.DeadLetterProcessor;
import queues.QueueProcessor;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket clientSocket = new ServerSocket(9099);
        ServerSocket serverSocket = new ServerSocket(9090);

        UserRepository userRepository = new UserRepository();
        EventQueue eventQueue = new EventQueue();
        DeadLetterEventQueue deadLetterEventQueue = new DeadLetterEventQueue();

        Thread eventThread = new Thread(new EventProcessor(eventQueue, deadLetterEventQueue, serverSocket));
        Thread clientThread = new Thread(new ClientProcessor(userRepository, clientSocket));
        Thread queueThread = new Thread(new QueueProcessor(eventQueue, userRepository, deadLetterEventQueue));
        Thread deadLetterThread = new Thread(new DeadLetterProcessor(deadLetterEventQueue));

        eventThread.start();
        clientThread.start();
        queueThread.start();
        deadLetterThread.start();

        try {
            eventThread.join();
            clientThread.join();
            queueThread.join();
            deadLetterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
