package events;

import clients.RepositoryInterface;
import clients.User;
import exceptions.EventException;

public class PrivateMessage extends BaseEvent {
    private static final String PRIVATE_MESSAGE_PAYLOAD_PATTERN = "%d|P|%d|%d";
    private final int fromUserId;
    private int toUser;

    public PrivateMessage(int sequenceNumber, int fromUserId, int toUser) {
        super(sequenceNumber);
        this.fromUserId = fromUserId;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format(PRIVATE_MESSAGE_PAYLOAD_PATTERN, sequenceNumber, fromUserId, toUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) throws EventException {
        User user = clientRepository.get(toUser);
        user.emit(this);
        if (user.offlineUser) {
            throw new EventException("Event cannot be published", this.toString());
        }
    }
}
