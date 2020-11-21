package events;

import queues.EventQueueInterface;

import java.util.Collection;
import java.util.concurrent.PriorityBlockingQueue;

public class EventQueue implements EventQueueInterface {
    private PriorityBlockingQueue<BaseEvent> queue = new PriorityBlockingQueue<>();

    @Override
    public void add(BaseEvent baseEvent) {
        queue.add(baseEvent);
    }

    public int count() {
        return queue.size();
    }

    @Override
    public Collection<BaseEvent> getAll() {
        return queue;
    }

    @Override
    public BaseEvent peek() {
        return queue.peek();
    }

    @Override
    public BaseEvent poll() {
        return queue.poll();
    }
}
