package events.factory;

import events.BaseEvent;
import events.Broadcast;
import utils.EventConstants;

public class BroadcastEventBuilder implements EventBuilder {
    @Override
    public String getType() {
        return EventConstants.B;
    }

    @Override
    public BaseEvent build(int sequenceNumber, String[] payloadList) {
        return new Broadcast(sequenceNumber);
    }
}
