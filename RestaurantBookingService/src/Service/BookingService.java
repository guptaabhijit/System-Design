package Service;

import Models.Booking;
import Models.BookingStatus;
import Models.RestrauntTables;

import java.time.Instant;
import java.util.*;

public class BookingService {

    final RestrauntService restrauntService;

    final Map<String, Booking> bookings;

    public BookingService(RestrauntService restrauntService) {
        this.restrauntService = restrauntService;
        this.bookings = new HashMap<>();
    }

    public Booking bookTable(final String userId, final int restrauntId, final Date on, final int fromTime, final int endTime){
        List<RestrauntTables> result = restrauntService.searchRestraunt(restrauntId, on, fromTime, endTime);
        Optional<RestrauntTables> optional = result.stream()
                .filter(st -> st.fromTime >= fromTime)
                .filter(st -> st.endTime <= endTime ).findAny();

        if(!optional.isPresent())
            throw new RuntimeException("No table available for give date");

        RestrauntTables tables = optional.get();
        synchronized (tables){
            if(tables.noOfTableLeft>0) {
                Booking newBooking = new Booking();
                newBooking.bookingId = UUID.randomUUID().toString();
                newBooking.userId = userId;
                newBooking.bookedAt = Date.from(Instant.now());
                newBooking.bookingStatus = BookingStatus.RESERVED;
                newBooking.restrauntId = tables.restrauntId;
                newBooking.fromTime = tables.fromTime;
                newBooking.endTime = tables.endTime;
                newBooking.tableSlotId = tables.tableSlotId;
                --tables.noOfTableLeft;
                bookings.put(newBooking.bookingId,newBooking);
                return newBooking;
            }else
                throw new RuntimeException("TableSlots are full for the given time. Please select any other slot");
        }
    }



    private void validateBookingId(final String bookingId){
        if(!bookings.containsKey(bookingId))
            throw new RuntimeException("No booking found with bookingId :" + bookingId);
    }

    Booking confirmBooking(final String bookingId){
        validateBookingId(bookingId);
        Booking booking = bookings.get(bookingId);
        booking.bookingStatus = BookingStatus.CONFIRMED;
        return booking;
    }

    public Booking cancelBooking(final String bookingId){

        validateBookingId(bookingId);
        Booking booking = bookings.get(bookingId);
        if(BookingStatus.CANCELLED.equals(booking.bookingStatus))
            throw new RuntimeException("Booking already cancelled");

        Optional<RestrauntTables> optional = restrauntService.restrauntTables.stream()
                .filter(rt -> rt.tableSlotId == booking.tableSlotId).findAny();
        if(!optional.isPresent())
            throw new RuntimeException("Invalid booking to cancel : " + bookingId);
        RestrauntTables restrauntTable = optional.get();
        synchronized (restrauntTable){
            ++restrauntTable.noOfTableLeft;
        }
        booking.bookingStatus = BookingStatus.CANCELLED;
        return booking;
    }
}
