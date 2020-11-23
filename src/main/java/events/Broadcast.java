package events;

import clients.User;
import clients.RepositoryInterface;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class Broadcast extends BaseEvent {

    private static final String BROADCAST_PAYLOAD_PATTERN = "%d|B";

    public Broadcast(int sequenceNumber) {
        super(sequenceNumber);
    }

    @Override
    public String toString() {
        return String.format(BROADCAST_PAYLOAD_PATTERN, sequenceNumber);
    }

    @Override
    public void get(RepositoryInterface clientRepository) throws EventException {
        AtomicBoolean success = new AtomicBoolean(true);
        Collection<User> users = clientRepository.getAll();
        users.forEach(client -> {
            if(!client.emit(this)) {
                success.set(false);
            }
        });

        if (!success.get()) {
            throw new EventException("Event cannot be published!", this.toString());
        }
    }
}
