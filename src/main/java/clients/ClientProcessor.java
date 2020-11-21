package clients;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientProcessor implements Runnable {
    private final UserRepository userRepository;
    private final ServerSocket serverSocket;

    public ClientProcessor(UserRepository userRepository, ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        try {
            Socket clientSocket = serverSocket.accept();
            while (clientSocket != null) {
                process(clientSocket);
                clientSocket = serverSocket.accept();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void process(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientId = reader.readLine();
            if (clientId != null) {
                userRepository.add(Integer.parseInt(clientId), socket);
                System.out.println("User connected: " + clientId);
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
