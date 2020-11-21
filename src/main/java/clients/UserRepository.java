package clients;

import utils.NullStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements RepositoryInterface {
    private final ConcurrentHashMap<Integer, User> clients = new ConcurrentHashMap<>();

    public void add(int id, Socket socket) throws IOException {
        add(id, socket.getOutputStream());
    }

    private void add(int id, OutputStream stream) {
        User user = new User(id, new PrintWriter(new OutputStreamWriter(stream)));
        clients.put(id, user);
    }

    @Override
    public User get(int id) {
        if (!clients.containsKey(id)) {
            return createIfNotExist(id);
        }
        return clients.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return clients.values();
    }

    private User createIfNotExist(int id) {
        User user = new User(id, new PrintWriter(new NullStream()));
        clients.put(id, user);
        return user;
    }

}
