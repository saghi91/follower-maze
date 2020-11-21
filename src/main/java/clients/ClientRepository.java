package clients;

import utils.NullStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ClientRepository implements ClientRepositoryInterface {
    private final ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();

    void add(int id, Socket socket) throws IOException {
        add(id, socket.getOutputStream());
    }

    private void add(int id, OutputStream stream) {
        Client client = new Client(id, new PrintWriter(new OutputStreamWriter(stream)));
        clients.put(id, client);
    }

    @Override
    public Client get(int id) {
        if (!clients.containsKey(id)) {
            return createIfNotExist(id);
        }
        return clients.get(id);
    }

    private Client createIfNotExist(int id) {
        Client client = new Client(id, new PrintWriter(new NullStream()));
        clients.put(id, client);
        return client;
    }

    @Override
    public Collection<Client> getAll() {
        return clients.values();
    }

}
