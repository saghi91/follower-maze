package events;

import clients.RepositoryInterface;
import clients.User;
import exceptions.EventException;

import java.util.Collection;

public class StatusUpdate extends BaseEvent {
    private static final String STATUS_UPDATE_PAYLOAD_PATTERN = "%d|S|%d";
    private final int fromUser;

    public StatusUpdate(int sequenceNumber, int fromUser) {
        super(sequenceNumber);
        this.fromUser = fromUser;
    }

    @Override
    public String toString() {
        return String.format(STATUS_UPDATE_PAYLOAD_PATTERN, sequenceNumber, fromUser);
    }

    @Override
    public void get(RepositoryInterface clientRepository) throws EventException {
        User user = clientRepository.get(fromUser);
        Collection<Integer> followers = user.getFollowers();
        followers.forEach(followerId -> {
            User follower = clientRepository.get(followerId);
            if (!follower.emit(this)) {
                user.removeFollower(follower.getId());
            }
        });

        if (user.offlineUser) {
            throw new EventException("event cannot be published!", this.toString());
        }
    }
}
