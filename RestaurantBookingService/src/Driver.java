import Models.Booking;
import Models.Restaurant;
import Models.RestrauntSchedule;
import Service.BookingService;
import Service.RestrauntService;
import Service.RestrauntTableScheduler;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class Driver {
    public static void main(String[] args) {
        RestrauntTableScheduler tableScheduler = new RestrauntTableScheduler(5,1);
        RestrauntService restrauntService = new RestrauntService(tableScheduler);

        tableScheduler.restrauntService = restrauntService;
        List<RestrauntSchedule> restrauntSchedules = new ArrayList<>();

        RestrauntSchedule schedule = new RestrauntSchedule();
        schedule.dayOfWeek = DayOfWeek.WEDNESDAY.getValue()+1;
        schedule.fromTime = 1000;
        schedule.endTime = 1300;
        schedule.id = 1;
        restrauntSchedules.add(schedule);

        schedule = new RestrauntSchedule();
        schedule.dayOfWeek = DayOfWeek.WEDNESDAY.getValue()+1;
        schedule.fromTime = 1800;
        schedule.endTime = 2400;
        schedule.id = 2;
        restrauntSchedules.add(schedule);

        Restaurant restraunt = restrauntService.registerRestraunt("Dhaba",1,restrauntSchedules);
        BookingService bookingService = new BookingService(restrauntService);

        int[] startTimings = new int[]{ 1000, 1100, 1200, 1300, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400 };
        int size = startTimings.length;

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for(int i=0; i< 15; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int startTime = startTimings[ThreadLocalRandom.current().nextInt(0, size - 1)];
                    Booking booking = new Booking();
                    booking.fromTime = startTime;
                    booking.endTime = startTime+100;
                    booking.restrauntId = restraunt.id;
                    booking.userId = "nickpro";
                    try {
                        Thread.sleep(startTime);
                        booking = bookingService.bookTable("nickpro", restraunt.id, Date.from(Instant.now()), startTime, startTime + 100);
                        System.out.println("Booking Done :" + booking.fromTime + "-" + booking.endTime);
                        booking = bookingService.cancelBooking(booking.bookingId);
                        System.out.println("Booking Canc :" + booking.fromTime + "-" + booking.endTime);
                    } catch (Exception ex) {
                        System.out.println("Booking fail :" + booking.fromTime + "-" + booking.endTime+ "-" + ex.getMessage());
                    }
                }
            });
        }
        executorService.shutdown();

    }
}
