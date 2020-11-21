package events;

import clients.Client;
import clients.ClientRepositoryInterface;

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
    public void get(ClientRepositoryInterface clientRepository) {
        Client client = clientRepository.get(fromUser);
        Collection<Integer> followers = client.getFollowers();
        followers.forEach(followerId -> {
            Client follower = clientRepository.get(followerId);
            boolean used = follower.useEvent(this);
            if (!used) {
                client.removeFollower(follower.getId());
            }
        });
    }
}
