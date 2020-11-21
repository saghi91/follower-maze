package events;

import clients.Client;
import clients.ClientRepositoryInterface;

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
    public void get(ClientRepositoryInterface clientRepository) {
        Client client = clientRepository.get(toUser);
        client.removeFollower(fromUser);
    }
}
