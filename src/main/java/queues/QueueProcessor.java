package queues;

import clients.UserRepository;
import clients.RepositoryInterface;
import events.BaseEvent;
import events.EventQueue;

public class QueueProcessor implements Runnable {
    private final RepositoryInterface clientRepository;
    private final QueueInterface eventQueue;
    private int sequenceNumber = 1;

    public QueueProcessor(EventQueue eventQueue, UserRepository userRepository) {
        this.eventQueue = eventQueue;
        this.clientRepository = userRepository;
    }

    @Override
    public void run() {
        while (true) {
            BaseEvent baseEvent = eventQueue.peek();
            if (baseEvent != null) {
                process(baseEvent);
            }
        }
    }
    private void process(BaseEvent baseEvent) {
        if (baseEvent.sequenceNumber <= sequenceNumber) {
            try {
                baseEvent = eventQueue.poll();
                baseEvent.get(clientRepository);
                sequenceNumber++;
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
