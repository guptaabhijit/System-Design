import Models.Event;
import Models.User;
import Repository.UserRepo;
import Service.CalendarService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarApp {
    public static void main(String[] args) {

        // Create users
        User user1 = new User("John");
        User user2 = new User("Alice");
        User user3 = new User("Bob");


        UserRepo userRepo = new UserRepo();
        CalendarService calendarService = new CalendarService(userRepo);

        // Add Users in CalendarService
        calendarService.addUser(user1);
        calendarService.addUser(user2);
        calendarService.addUser(user3);

        // Add availability for each users
        user1.addAvailability(LocalDateTime.of(2023, 7, 2, 9, 0), LocalDateTime.of(2023, 7, 2, 17, 0));
        user2.addAvailability(LocalDateTime.of(2023, 7, 2, 10, 0), LocalDateTime.of(2023, 7, 2, 18, 0));
        user3.addAvailability(LocalDateTime.of(2023, 7, 2, 11, 0), LocalDateTime.of(2023, 7, 2, 19, 0));

        // Create 1st event
        List<User> participants1 = new ArrayList<>();
        participants1.add(user2);
        participants1.add(user3);
        calendarService.createEvent(user1, "Meeting-1", LocalDateTime.of(2023, 7, 2, 14, 0), LocalDateTime.of(2023, 7, 2, 16, 0), participants1);


        // Create 2nd event
        List<User> participants2 = new ArrayList<>();
        participants2.add(user2);
        calendarService.createEvent(user1, "Meeting-2", LocalDateTime.of(2023, 7, 2, 15, 0), LocalDateTime.of(2023, 7, 2, 17, 0), participants2);




        // Create 3rd event
        List<User> participants3 = new ArrayList<>();
        participants3.add(user2);
        calendarService.createEvent(user1, "Meeting-3", LocalDateTime.of(2023, 7, 2, 17, 30), LocalDateTime.of(2023, 7, 2, 18, 0), participants3);

        // Get user events
        List<Event> user1Events = calendarService.getUserEvents(user2);
        for (Event event : user1Events) {
            System.out.println(event.getTitle() + ": " + event.getStartTime() + " to " + event.getEndTime());
        }


        // Get conflicting events for a user on a specific date
//        List<Event> conflictingEvents = calendarService.getConflictingEvents(user2, LocalDateTime.of(2023, 7, 2, 0, 0));
//        for (Event event : conflictingEvents) {
//            System.out.println("Conflicting Event: " + event.getTitle());
//        }



        // Find a favorable slot for a group of users
        LocalDateTime favorableSlot = calendarService.getFavorableSlot(participants1, LocalDateTime.of(2023, 7, 2, 14, 0),
                LocalDateTime.of(2023, 7, 2, 23, 0), 30);
        if (favorableSlot != null) {
            System.out.println("Favorable Slot: " + favorableSlot);
        } else {
            System.out.println("No favorable slot found.");
        }
    }
}
