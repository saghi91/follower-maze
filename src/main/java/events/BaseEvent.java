package events;

import clients.ClientRepositoryInterface;

public abstract class BaseEvent implements Comparable<BaseEvent> {
    public final int sequenceNumber;

    public BaseEvent(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public int compareTo(BaseEvent baseEvent) {
        return Integer.compare(sequenceNumber, baseEvent.sequenceNumber);
    }

    public abstract void get(ClientRepositoryInterface clientRepository);
}
