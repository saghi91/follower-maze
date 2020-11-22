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

    public QueueProcessor(EventQueue eventQueue, UserRepository userRepository, DeadLetterQueueInterface deadLetterQueueInterface) {
        this.eventQueue = eventQueue;
        this.clientRepository = userRepository;
        this.deadLetterQueueInterface = deadLetterQueueInterface;
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
                if (baseEvent.hasIssue) {
                    deadLetterQueueInterface.add(baseEvent.toString());
                }
                sequenceNumber++;
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
