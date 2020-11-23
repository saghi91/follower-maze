package queues;

import clients.RepositoryInterface;
import clients.UserRepository;
import events.BaseEvent;
import events.EventException;

public class QueueProcessor implements Runnable {
    private final RepositoryInterface userRepository;
    private final QueueInterface<BaseEvent> eventQueue;
    private final QueueInterface<String> deadLetterQueue;
    private int sequenceNumber = 1;

    public QueueProcessor(EventQueue eventQueue, UserRepository userRepository,
                          DeadLetterQueue deadLetterQueue) {
        this.eventQueue = eventQueue;
        this.userRepository = userRepository;
        this.deadLetterQueue = deadLetterQueue;
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
                baseEvent.get(userRepository);
                sequenceNumber++;
            } catch (EventException e) {
                deadLetterQueue.add(e.getRawPayload());
                sequenceNumber++;
            }
        }
    }
}
