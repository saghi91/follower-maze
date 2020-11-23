package events;

import clients.RepositoryInterface;
import clients.User;

public class Unfollow extends BaseEvent {
    private static final String UNFOLLOW_PAYLOAD_PATTERN = "%d|U|%d|%d";
    private final int fromUser;
    private final int toUser;

    public Unfollow(int sequenceNumber, int fromUser, int toUser) {
        super(sequenceNumber);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format(UNFOLLOW_PAYLOAD_PATTERN, sequenceNumber, fromUser, toUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) {
        User user = clientRepository.get(toUser);
        user.removeFollower(fromUser);
    }
}
