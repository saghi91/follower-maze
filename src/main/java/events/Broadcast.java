package events;

import clients.Client;
import clients.ClientRepositoryInterface;

import java.util.Collection;

public class Broadcast extends BaseEvent {
    public Broadcast(int sequenceNumber) {
        super(sequenceNumber);
    }

    @Override
    public String toString() {
        return String.format("%d|B", sequenceNumber);
    }

    @Override
    public void get(ClientRepositoryInterface clientRepository) {
        Collection<Client> clients = clientRepository.getAll();
        clients.forEach(client -> client.useEvent(this));
    }
}
