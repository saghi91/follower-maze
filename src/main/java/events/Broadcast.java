package events;

import clients.User;
import clients.RepositoryInterface;

import java.util.Collection;

public class Broadcast extends BaseEvent {
    public Broadcast(int sequenceNumber) {
        super(sequenceNumber);
    }

    @Override
    public String toString() {
        return String.format("%d|B", sequenceNumber);
    }

    @Override
    public void get(RepositoryInterface clientRepository) {
        Collection<User> users = clientRepository.getAll();
        users.forEach(client -> client.useEvent(this));
    }
}
