package queues;

import java.util.Collection;
import java.util.concurrent.PriorityBlockingQueue;

public class DeadLetterQueue implements QueueInterface<String> {
    private PriorityBlockingQueue<String> deadLetter = new PriorityBlockingQueue<>();

    @Override
    public void add(String rawPayload) {
        deadLetter.add(rawPayload);
    }

    @Override
    public String peek() {
        return deadLetter.peek();
    }

    @Override
    public String poll() {
        return deadLetter.poll();
    }

    @Override
    public void remove() {
        deadLetter.remove();
    }

    @Override
    public Collection<String> getAll() {
        return deadLetter;
    }
}
