package events.factory;

import events.BaseEvent;
import utils.EventConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EventFactory {
    public static BaseEvent create(String rawPayload) {

        String[] payloadParts = rawPayload.split(EventConstants.PAYLOAD_DELIMITER);
        Set<EventBuilder> eventTypes = new HashSet<>(Arrays.asList(getValues()));

        int sequenceNumber = Integer.parseInt(payloadParts[EventConstants.SEQUENCE_INDEX]);
        String type = payloadParts[EventConstants.TYPE_INDEX];

        for (EventBuilder eventBuilder : eventTypes) {
            if (eventBuilder.getType().equals(type)) {
                return eventBuilder.build(sequenceNumber, payloadParts);
            }
        }

        throw new RuntimeException("Event type not found");
    }

    private static EventBuilder[] getValues() {
        return new EventBuilder[] {
            new UnfollowEventBuilder(),
            new BroadcastEventBuilder(),
            new PrivateMessageEventBuilder(),
            new FollowEventBuilder(),
            new StatusUpdateEventBuilder()
        };
    }
}