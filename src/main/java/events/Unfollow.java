package events;

import clients.RepositoryInterface;
import clients.User;

public class Unfollow extends BaseEvent {
    private final int fromUser;
    private final int toUser;

    public Unfollow(int sequenceNumber, int fromUser, int toUser) {
        super(sequenceNumber);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format("%d|U|%d|%d", sequenceNumber, fromUser, toUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) {
        User user = clientRepository.get(toUser);
        user.removeFollower(fromUser);
    }
}
