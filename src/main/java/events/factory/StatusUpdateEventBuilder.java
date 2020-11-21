package events.factory;

import events.BaseEvent;
import events.StatusUpdate;
import utils.EventConstants;

public class StatusUpdateEventBuilder implements EventBuilder {
    @Override
    public String getType() {
        return EventConstants.S;
    }

    @Override
    public BaseEvent build(int sequenceNumber, String[] payloadList) {
        int fromUser = Integer.parseInt(payloadList[EventConstants.FROM_USER_INDEX]);

        return new StatusUpdate(sequenceNumber, fromUser);
    }
}
