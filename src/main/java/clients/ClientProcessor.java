package clients;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientProcessor implements Runnable {
    private final ClientRepository clientRepository;
    private final ServerSocket serverSocket;

    public ClientProcessor(ClientRepository clientRepository, ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.clientRepository = clientRepository;
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
                clientRepository.add(Integer.parseInt(clientId), socket);
                System.out.println("User connected: " + clientId);
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
