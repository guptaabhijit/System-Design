package Repository;

import Models.Event;
import java.util.List;
import java.util.ArrayList;

public class EventRepo {

    private List<Event> events;

    public EventRepo(){
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void deleteEvent(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return events;
    }

}
