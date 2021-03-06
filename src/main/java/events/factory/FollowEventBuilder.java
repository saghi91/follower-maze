package events.factory;

import events.BaseEvent;
import events.Follow;
import utils.EventConstants;

public class FollowEventBuilder implements EventBuilder {
    @Override
    public String getType() {
        return EventConstants.F;
    }

    @Override
    public BaseEvent build(int sequenceNumber, String[] payloadList) {
        int fromUser = Integer.parseInt(payloadList[EventConstants.FROM_USER_INDEX]);
        int toUser = Integer.parseInt(payloadList[EventConstants.TO_USER_INDEX]);

        return new Follow(sequenceNumber, fromUser, toUser);
    }
}
