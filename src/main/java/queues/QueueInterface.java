package queues;

import java.util.Collection;

public interface QueueInterface<T> {
    void add(T item);
    T peek();
    T poll();
    void remove();
    Collection<T> getAll();
}
