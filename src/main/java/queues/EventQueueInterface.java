package queues;

import events.BaseEvent;

import java.util.Collection;

public interface EventQueueInterface {
    void add(BaseEvent baseEventHandler);
    BaseEvent peek();
    BaseEvent poll();
    int count();
    Collection<BaseEvent> getAll();
}
