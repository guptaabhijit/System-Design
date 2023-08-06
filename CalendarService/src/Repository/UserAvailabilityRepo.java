package Repository;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;


public class UserAvailabilityRepo {

    private List<LocalDateTime> availability;

    public UserAvailabilityRepo() {
        this.availability = new ArrayList<>();
    }

    public void addAvailability(LocalDateTime startTime, LocalDateTime endTime) {
        availability.add(startTime);
        availability.add(endTime);
    }

    public List<LocalDateTime> getAvailability() {
        return availability;
    }
}

// 9 to 5
// 10 to 6
// 11 to 7
