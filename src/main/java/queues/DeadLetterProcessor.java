package queues;

public class DeadLetterProcessor implements Runnable {
    private final DeadLetterQueueInterface deadLetterQueueInterface;

    public DeadLetterProcessor(DeadLetterQueueInterface deadLetterQueueInterface) {
        this.deadLetterQueueInterface = deadLetterQueueInterface;
    }

    @Override
    public void run() {
        while(true) {
            String rawPayload = deadLetterQueueInterface.peek();
            if (rawPayload != null) {
                process(rawPayload);
            }
        }
    }

    private void process(String rawPayload) {
        if (deadLetterQueueInterface.getAll().contains(rawPayload)) {
            deadLetterQueueInterface.remove();
        }
    }
}