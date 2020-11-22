package events;

import clients.User;
import clients.RepositoryInterface;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

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
        AtomicBoolean success = new AtomicBoolean(true);
        Collection<User> users = clientRepository.getAll();
        users.forEach(client -> {
            if(!client.emit(this)) {
                success.set(false);
            }
        });

        if (!success.get()) {
            this.hasIssue = false;
        }
    }
}
