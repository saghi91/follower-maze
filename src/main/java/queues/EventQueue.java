package queues;

import events.BaseEvent;
import java.util.Collection;
import java.util.concurrent.PriorityBlockingQueue;

public class EventQueue implements QueueInterface<BaseEvent> {
    private PriorityBlockingQueue<BaseEvent> queue = new PriorityBlockingQueue<>();

    @Override
    public void add(BaseEvent baseEvent) {
        queue.add(baseEvent);
    }

    @Override
    public Collection<BaseEvent> getAll() {
        return queue;
    }

    @Override
    public BaseEvent peek() {
        BaseEvent data = null;
        try {
            data = queue.take();
            queue.add(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public BaseEvent poll() {
        return queue.poll();
    }

    @Override
    public void remove() {
        queue.remove();
    }
}
