package events.factory;

import events.BaseEvent;

public interface EventBuilder {
    String getType();
    BaseEvent build(int sequenceNumber, String[] payloadList);
}
