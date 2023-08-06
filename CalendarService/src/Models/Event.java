package Models;

import java.util.List;
import Repository.UserRepo;
import java.time.LocalDateTime;


public class Event {
    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private User organizer;

    private UserRepo userRepo;

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime, User organizer, UserRepo userRepo) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.userRepo = userRepo;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }


    public void addParticipant(User participant) {
        userRepo.addParticipant(participant);
    }

    public void removeParticipant(User participant) {
        userRepo.removeParticipant(participant);
    }

    public List<User> getParticipants() {
        return userRepo.getParticipants();
    }

}
