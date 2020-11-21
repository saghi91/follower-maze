package events;

import clients.User;
import clients.RepositoryInterface;

public class Follow extends BaseEvent {
    private final int fromUser;
    private final int toUser;

    public Follow(int sequenceNumber, int fromUser, int toUser) {
        super(sequenceNumber);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    @Override
    public String toString() {
        return String.format("%d|F|%d|%d", sequenceNumber, fromUser, toUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) {
        User user = clientRepository.get(toUser);
        user.addFollower(fromUser);
        user.useEvent(this);
    }
}
