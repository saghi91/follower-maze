package events.factory;

import events.BaseEvent;
import events.DeadLetterEventQueue;
import exceptions.EventException;
import utils.EventConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EventFactory {
    public static BaseEvent create(String rawPayload) throws EventException {

        int sequenceNumber;
        String[] payloadParts = rawPayload.split(EventConstants.PAYLOAD_DELIMITER);
        Set<EventBuilder> eventTypes = new HashSet<>(Arrays.asList(getValues()));

        try {
            sequenceNumber = Integer.parseInt(payloadParts[EventConstants.SEQUENCE_INDEX]);
        } catch (NumberFormatException e) {
            throw new EventException("sequence number invalid", rawPayload);
        }

        String type = payloadParts[EventConstants.TYPE_INDEX];

        try {
            for (EventBuilder eventBuilder : eventTypes) {
                if (eventBuilder.getType().equals(type)) {
                    return eventBuilder.build(sequenceNumber, payloadParts);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EventException("from/to user index invalid", rawPayload);
        }


        throw new EventException("event cannot be created", rawPayload);
    }

    private static EventBuilder[] getValues() {
        return new EventBuilder[]{
                new UnfollowEventBuilder(),
                new BroadcastEventBuilder(),
                new PrivateMessageEventBuilder(),
                new FollowEventBuilder(),
                new StatusUpdateEventBuilder()
        };
    }
}
