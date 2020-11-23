package queues;

public class DeadLetterProcessor implements Runnable {
    private final QueueInterface<String> deadLetterQueue;

    public DeadLetterProcessor(QueueInterface<String> deadLetterQueue) {
        this.deadLetterQueue = deadLetterQueue;
    }

    @Override
    public void run() {
        String rawPayload = deadLetterQueue.peek();
        while (rawPayload != null) {
            process(rawPayload);
            rawPayload = deadLetterQueue.peek();
        }
    }

    private void process(String rawPayload) {
        if (deadLetterQueue.getAll().contains(rawPayload)) {
            deadLetterQueue.remove();
        }
    }
}
