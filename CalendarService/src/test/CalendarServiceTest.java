package test;

import Models.Event;
import Models.User;
import Repository.UserRepo;
import Service.CalendarService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CalendarServiceTest {

    public static void main(String[] args) {
        testCreateEvent();
        testDeleteEvent();
        testGetUserEvents();
        testGetConflictingEvents();
        testCreateRecurringEvent();
    }

    public static void testCreateEvent() {
        // Create users
        User user1 = new User("Abhijit");
        User user2 = new User("Rahul");
        UserRepo userRepo = new UserRepo();

        // Create the calendar service
        CalendarService calendarService = new CalendarService(userRepo);

        // Add users to the calendar service
        calendarService.addUser(user1);
        calendarService.addUser(user2);

        // Create an event
        calendarService.createEvent(user1, "Meeting", LocalDateTime.of(2023, 7, 2, 14, 0), LocalDateTime.of(2023, 7, 2, 16, 0), Collections.singletonList(user2));

        // Get user events
        List<Event> user1Events = calendarService.getUserEvents(user1);
        assert user1Events.size() == 1;

        System.out.println("createEvent test passed.");
    }

    public static void testDeleteEvent() {
        // Create users
        User user1 = new User("Abhijit");
        User user2 = new User("Rahul");
        UserRepo userRepo = new UserRepo();

        // Create the calendar service
        CalendarService calendarService = new CalendarService(userRepo);

        // Add users to the calendar service
        calendarService.addUser(user1);
        calendarService.addUser(user2);

        // Create an event
       calendarService.createEvent(user1, "Meeting", LocalDateTime.of(2023, 7, 2, 14, 0), LocalDateTime.of(2023, 7, 2, 16, 0), Collections.singletonList(user2));

        List<Event> user1Events = calendarService.getUserEvents(user1);
        // Delete the event
        calendarService.deleteEvent(user1, user1Events.get(0));

        // Get user events
        user1Events = calendarService.getUserEvents(user1);
        assert user1Events.isEmpty();

        System.out.println("deleteEvent test passed.");
    }


    public static void testGetUserEvents() {
        // Create users
        User user1 = new User("Abhijit");
        User user2 = new User("Rahul");
        UserRepo userRepo = new UserRepo();

        // Create the calendar service
        CalendarService calendarService = new CalendarService(userRepo);

        // Add users to the calendar service
        calendarService.addUser(user1);
        calendarService.addUser(user2);

        // Create events
        calendarService.createEvent(user1, "Meeting 1", LocalDateTime.of(2023, 7, 2, 10, 0), LocalDateTime.of(2023, 7, 2, 12, 0), Collections.singletonList(user2));
        calendarService.createEvent(user1, "Meeting 2", LocalDateTime.of(2023, 7, 3, 14, 0), LocalDateTime.of(2023, 7, 3, 16, 0), Collections.singletonList(user2));

        // Get user events
        List<Event> user1Events = calendarService.getUserEvents(user1);
        assert user1Events.size() == 2;

        System.out.println("getUserEvents test passed.");
    }

    public static void testGetConflictingEvents() {
        // Create users
        User user1 = new User("Abhijit");
        User user2 = new User("Rahul");
        UserRepo userRepo = new UserRepo();

        // Create the calendar service
        CalendarService calendarService = new CalendarService(userRepo);

        // Add users to the calendar service
        calendarService.addUser(user1);
        calendarService.addUser(user2);

        // Create events
        calendarService.createEvent(user1, "Meeting 1", LocalDateTime.of(2023, 7, 2, 10, 0), LocalDateTime.of(2023, 7, 2, 12, 0), Collections.singletonList(user2));
        calendarService.createEvent(user2, "Meeting 2", LocalDateTime.of(2023, 7, 2, 11, 0), LocalDateTime.of(2023, 7, 2, 13, 0), Collections.singletonList(user1));

        // Get conflicting events for user1
        List<Event> conflictingEvents = calendarService.getConflictingEvents(user1, LocalDateTime.of(2023, 7, 2, 0, 0));
        assert conflictingEvents.size() == 1;

        System.out.println("getConflictingEvents test passed.");
    }

    public static void testCreateRecurringEvent() {
        // Create users
        User organizer = new User("Abhijit");
        User user2 = new User("Rahul");
        UserRepo userRepo = new UserRepo();

        // Create the calendar service
        CalendarService calendarService = new CalendarService(userRepo);

        // Add users to the calendar service
        calendarService.addUser(organizer);
        calendarService.addUser(user2);

        // Create a recurring event
        calendarService.createRecurringEvent(organizer, "Daily Standup", LocalDateTime.of(2023, 7, 2, 10, 0), LocalDateTime.of(2023, 7, 2, 10, 30), Collections.singletonList(user2), 5);

        // Get user events
        List<Event> organizerEvents = calendarService.getUserEvents(organizer);
        assert organizerEvents.size() == 5;

        System.out.println("createRecurringEvent test passed.");
    }

}
