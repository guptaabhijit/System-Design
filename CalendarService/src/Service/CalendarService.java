package Service;

import Models.User;
import Models.Event;
import java.util.List;
import java.util.ArrayList;
import Repository.UserRepo;
import java.time.LocalDateTime;


public class CalendarService {

    private UserRepo userRepo;

    public CalendarService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void addUser(User user) {
        userRepo.addUsers(user);
    }

    public void createEvent(User organizer, String title, LocalDateTime startTime, LocalDateTime endTime, List<User> participants) {
        Event event = new Event(title, startTime, endTime, organizer, userRepo);
        organizer.addEvent(event);
        for (User participant : participants) {
            participant.addEvent(event);
            event.addParticipant(participant);
        }
    }

    public void deleteEvent(User user, Event event) {
        // Condition for organizer.
        user.deleteEvent(event);
        for (User participant : event.getParticipants()) {
            participant.deleteEvent(event);
        }
    }

    public List<Event> getUserEvents(User user) {
        return user.getEvents();
    }

    private List<Event> getEventsForDate(User user, LocalDateTime date) {
        List<Event> eventsForDate = new ArrayList<>();
        for (Event event : user.getEvents()) {
            LocalDateTime eventDate = event.getStartTime().toLocalDate().atStartOfDay();
            if (eventDate.equals(date)) {
                eventsForDate.add(event);
            }
        }
        return eventsForDate;
    }

    public List<Event> getConflictingEvents(User user, LocalDateTime date) {
        List<Event> conflictingEvents = new ArrayList<>();
        List<Event> eventsForDate = getEventsForDate(user, date);
// 10-11, 12-1, 3-4, 3:30 - 4:30

        for (int i = 0; i < eventsForDate.size(); i++) {
            Event event1 = eventsForDate.get(i);
            for (int j = i + 1; j < eventsForDate.size(); j++) {
                Event event2 = eventsForDate.get(j);
                if (event2.getStartTime().isBefore(event1.getEndTime()) && event2.getEndTime().isAfter(event1.getStartTime())) {
                    conflictingEvents.add(event1);
                    conflictingEvents.add(event2);
                }
            }
        }

        return conflictingEvents;
    }
// 9 - 6
//
//User1: 10-11, 1-2
//User2: 11-12, 2-3
// 12-1

    public LocalDateTime getFavorableSlot(List<User> users, LocalDateTime startDateTime, LocalDateTime endDateTime, int duration) {
        LocalDateTime favorableSlot = startDateTime;
        while (favorableSlot.plusMinutes(duration).isBefore(endDateTime)) {
            boolean isSlotAvailable = true;
            for (User user : users) {
                List<Event> userEvents = getEventsForDate(user, favorableSlot.toLocalDate().atStartOfDay());
                for (Event event : userEvents) {
                    if (event.getStartTime().isBefore(favorableSlot.plusMinutes(duration)) &&
                            event.getEndTime().isAfter(favorableSlot)) {
                        isSlotAvailable = false;
                        break;
                    }
                }
                if (!isSlotAvailable) {
                    break;
                }
            }
            if (isSlotAvailable) {
                return favorableSlot;
            }
            favorableSlot = favorableSlot.plusMinutes(30); // Increment by 30 minutes
        }
        return null; // No favorable slot found
    }


    // Create Recurring Event
    public void createRecurringEvent(User organizer, String title, LocalDateTime startTime, LocalDateTime endTime, List<User> participants, int recurrenceCount) {
        for (int i = 0; i < recurrenceCount; i++) {
            createEvent(organizer, title, startTime, endTime, participants);
            startTime = startTime.plusDays(1); // Increment the start time for the next recurrence
            endTime = endTime.plusDays(1); // Increment the end time for the next recurrence
        }
    }
}
