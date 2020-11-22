package events;

import clients.RepositoryInterface;
import clients.User;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public class StatusUpdate extends BaseEvent {
    private final int fromUser;

    public StatusUpdate(int sequenceNumber, int fromUser) {
        super(sequenceNumber);
        this.fromUser = fromUser;
    }

    @Override
    public String toString() {
        return String.format("%d|S|%d", sequenceNumber, fromUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) {
        AtomicBoolean success = new AtomicBoolean(true);
        User user = clientRepository.get(fromUser);
        Collection<Integer> followers = user.getFollowers();
        followers.forEach(followerId -> {
            User follower = clientRepository.get(followerId);
            if (!follower.emit(this)) {
                user.removeFollower(follower.getId());
                success.set(false);
            }
        });

        if (!user.offlineUser && success.get()) {
            this.hasIssue = true;
        }
    }
}
