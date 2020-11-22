package exceptions;

public class EventException extends Exception {
    private final String rawPayload;

    public EventException(String message, String rawPayload) {
        super(message);
        this.rawPayload = rawPayload;
    }

    public String getRawPayload() {
        return rawPayload;
    }
}
