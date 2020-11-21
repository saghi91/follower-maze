package events;

import clients.User;
import clients.RepositoryInterface;

public class PrivateMessage extends BaseEvent {
    private final int fromUserId;
    private int toUser;

    public PrivateMessage(int sequenceNumber, int fromUserId, int toUser) {
        super(sequenceNumber);
        this.fromUserId = fromUserId;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format("%d|P|%d|%d", sequenceNumber, fromUserId, toUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) {
        User user = clientRepository.get(toUser);
        user.useEvent(this);
    }
}
