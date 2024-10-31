package org.engine;

public class GameEvent {
    private String eventType;
    private String eventData;

    public GameEvent(String eventType, String eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventData() {
        return eventData;
    }
}
