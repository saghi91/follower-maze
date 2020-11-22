package events;

import clients.RepositoryInterface;

public abstract class BaseEvent implements Comparable<BaseEvent> {
    public boolean hasIssue = false;
    public final int sequenceNumber;

    public BaseEvent(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public int compareTo(BaseEvent baseEvent) {
        return Integer.compare(sequenceNumber, baseEvent.sequenceNumber);
    }

    public abstract void get(RepositoryInterface clientRepository);
}
