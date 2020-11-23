package queues;

import clients.RepositoryInterface;
import clients.UserRepository;
import events.BaseEvent;
import events.EventQueue;
import exceptions.EventException;

public class QueueProcessor implements Runnable {
    private final RepositoryInterface clientRepository;
    private final QueueInterface eventQueue;
    private final DeadLetterQueueInterface deadLetterQueueInterface;
    private int sequenceNumber = 1;
    private boolean running = true;

    public QueueProcessor(EventQueue eventQueue, UserRepository userRepository, DeadLetterQueueInterface deadLetterQueueInterface) {
        this.eventQueue = eventQueue;
        this.clientRepository = userRepository;
        this.deadLetterQueueInterface = deadLetterQueueInterface;
    }

    @Override
    public void run() {
        BaseEvent baseEvent = eventQueue.peek();
        while (baseEvent != null) {
            process(baseEvent);
            baseEvent = eventQueue.peek();
        }
    }

    private void process(BaseEvent baseEvent) {
        if (baseEvent.sequenceNumber <= sequenceNumber) {
            try {
                baseEvent = eventQueue.poll();
                baseEvent.get(clientRepository);
                sequenceNumber++;
            } catch (EventException e) {
                sequenceNumber++;
                deadLetterQueueInterface.add(e.getMessage());
            }
        }
    }
}
