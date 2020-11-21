package clients;

import java.util.Collection;

public interface ClientRepositoryInterface {
    Client get(int id);
    Collection<Client> getAll();
}
