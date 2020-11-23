package events;

import clients.RepositoryInterface;
import clients.User;

public class Follow extends BaseEvent {
    private static final String FOLLOW_PAYLOAD_PATTERN = "%d|F|%d|%d";
    private final int fromUser;
    private final int toUser;

    public Follow(int sequenceNumber, int fromUser, int toUser) {
        super(sequenceNumber);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format(FOLLOW_PAYLOAD_PATTERN, sequenceNumber, fromUser, toUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) throws EventException {
        User user = clientRepository.get(toUser);
        user.addFollower(fromUser);
        user.emit(this);
        if (user.isOfflineUser()) {
            throw new EventException("Event cannot be published!", this.toString());
        }
    }
}
