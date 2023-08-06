package Models;

import java.util.List;
import Repository.EventRepo;
import java.time.LocalDateTime;
import Repository.UserAvailabilityRepo;


public class User {

    private String name;
    private EventRepo eventRepo;
    private UserAvailabilityRepo userAvailabilityRepo;

    public User(String name) {
        this.name = name;
        this.eventRepo = new EventRepo();
        this.userAvailabilityRepo = new UserAvailabilityRepo();
    }

    public void addEvent(Event event) {
        eventRepo.addEvent(event);
    }

    public void deleteEvent(Event event) {
        eventRepo.deleteEvent(event);
    }

    public List<Event> getEvents() {
        return eventRepo.getEvents();
    }

    public void addAvailability(LocalDateTime startTime, LocalDateTime endTime) {
        userAvailabilityRepo.addAvailability(startTime, endTime);
    }

    public List<LocalDateTime> getAvailability() {
        return userAvailabilityRepo.getAvailability();
    }
}
