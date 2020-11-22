package queues;

import events.BaseEvent;

import java.util.Collection;

public interface QueueInterface {
    void add(BaseEvent baseEvent);
    BaseEvent peek();
    BaseEvent poll();
    Collection<BaseEvent> getAll();
}
