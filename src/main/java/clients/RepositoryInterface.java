package clients;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

public interface RepositoryInterface {
    void add(int id, Socket socket) throws IOException;
    User get(int id);
    Collection<User> getAll();
}
