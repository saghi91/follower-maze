import clients.ClientProcessor;
import clients.UserRepository;
import events.EventProcessor;
import events.EventQueue;
import queues.QueueProcessor;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket clientSocket = new ServerSocket(9099);
        ServerSocket serverSocket = new ServerSocket(9090);

        UserRepository userRepository = new UserRepository();
        EventQueue eventQueue = new EventQueue();

        Thread eventThread = new Thread(new EventProcessor(eventQueue, serverSocket));
        Thread clientThread = new Thread(new ClientProcessor(userRepository, clientSocket));
        Thread queueThread = new Thread(new QueueProcessor(eventQueue, userRepository));

        eventThread.start();
        clientThread.start();
        queueThread.start();

        try {
            eventThread.join();
            clientThread.join();
            queueThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
