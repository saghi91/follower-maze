package events;

import clients.User;
import clients.RepositoryInterface;

import java.util.Collection;

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
        User user = clientRepository.get(fromUser);
        Collection<Integer> followers = user.getFollowers();
        followers.forEach(followerId -> {
            User follower = clientRepository.get(followerId);
            boolean used = follower.useEvent(this);
            if (!used) {
                user.removeFollower(follower.getId());
            }
        });
    }
}
