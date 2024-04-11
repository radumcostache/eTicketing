package repository;

import model.Event;

import java.util.Vector;

public class EventRepository {

    Vector <Event> events = new Vector <Event> ();
    public EventRepository() {}
    public void add(Event event) {
        events.add(event);
    }

    public void printEvents() {
        System.out.println("******************************LIST OF EVENTS******************************");
        for (Event event : events) {
            System.out.println(event.getInfo());
        }
    }

    public Event lookupEvent(int eventId) {
        for (Event event : events) {
            if (event.getId() == eventId) {
                return event;
            }
        }
        return null;
    }
}
