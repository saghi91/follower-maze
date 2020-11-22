package queues;

import java.util.Collection;

public interface DeadLetterQueueInterface {
    void add(String rawPayload);
    String peek();
    String poll();
    String remove();
    Collection<String> getAll();
}
