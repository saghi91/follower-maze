package events;

import clients.RepositoryInterface;
import clients.User;

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
        if (!user.offlineUser) {
            this.hasIssue = true;
        }
        user.addFollower(fromUser);
        user.emit(this);
    }
}
