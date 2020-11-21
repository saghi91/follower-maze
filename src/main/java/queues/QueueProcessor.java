package queues;

import clients.ClientRepository;
import clients.ClientRepositoryInterface;
import events.BaseEvent;
import events.EventQueue;

public class QueueProcessor implements Runnable {
    private final ClientRepositoryInterface clientRepository;
    private final EventQueueInterface eventQueue;
    private int sequenceNumber = 1;

    public QueueProcessor(EventQueue eventQueue, ClientRepository clientRepository) {
        this.eventQueue = eventQueue;
        this.clientRepository = clientRepository;
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
