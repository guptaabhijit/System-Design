package Repository;

import Models.User;
import java.util.List;
import java.util.ArrayList;

public class UserRepo {

    private List<User> participants; // list of users for a particular event Participants <UserId> -> Events
    private List<User> users; // list of all users using calendar // User Table <Name, Id, >

    public UserRepo() {
        this.participants = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addUsers(User user){
        users.add(user);
    }

    public void addParticipant(User participant) {
        participants.add(participant);
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void removeParticipant(User participant) {
        participants.remove(participant);
    }
}

// Service -> Repository -> DB / List (In memory)