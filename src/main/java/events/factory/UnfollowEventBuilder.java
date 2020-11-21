package events.factory;

import events.BaseEvent;
import events.Unfollow;
import utils.EventConstants;

public class UnfollowEventBuilder implements EventBuilder {

    @Override
    public String getType() {
        return EventConstants.U;
    }

    @Override
    public BaseEvent build(int sequenceNumber, String[] payloadList) {
        int fromUser = Integer.parseInt(payloadList[EventConstants.FROM_USER_INDEX]);
        int toUser = Integer.parseInt(payloadList[EventConstants.TO_USER_INDEX]);

        return new Unfollow(sequenceNumber, fromUser, toUser);
    }
}
