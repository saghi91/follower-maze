package events.factory;

import events.BaseEvent;
import events.PrivateMessage;
import utils.EventConstants;

public class PrivateMessageEventBuilder implements EventBuilder {
    @Override
    public String getType() {
        return EventConstants.P;
    }

    @Override
    public BaseEvent build(int sequenceNumber, String[] payloadList) {
        int fromUser = Integer.parseInt(payloadList[EventConstants.FROM_USER_INDEX]);
        int toUser = Integer.parseInt(payloadList[EventConstants.TO_USER_INDEX]);

        return new PrivateMessage(sequenceNumber, fromUser, toUser);
    }
}
