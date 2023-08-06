package Models;

import java.util.Date;

public class Booking {

    public String bookingId;
    public String userId;
    public int restrauntId;
    public Date bookedAt;
    public BookingStatus bookingStatus;
    public int fromTime;
    public int endTime;

    public int tableSlotId;
}
