package christmas.domain.event;

import java.util.Collections;
import java.util.List;

public class EventManager {
    private static final EventManager INSTANCE = new EventManager();
    private final List<Event> events;

    private EventManager() {
        events = initializeEvents();
    }

    public static EventManager getInstance() {
        return INSTANCE;
    }

    private List<Event> initializeEvents() {
        return EventFactory.createEvents();
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }
}
