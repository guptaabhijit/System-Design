package Service;

import Models.Restaurant;
import Models.RestrauntSchedule;
import Models.RestrauntTables;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RestrauntTableScheduler {

    public RestrauntService restrauntService;
    final int daysToSchedule;

    final int slotDuration;

    public RestrauntTableScheduler(int daysToSchedule, int slotDuration) {
        this.daysToSchedule = daysToSchedule;
        this.slotDuration = slotDuration;
    }

    public List<RestrauntTables> createScheduleForARestraunt(Restaurant restraunt){
        List<RestrauntTables> restrauntTables = new ArrayList<>();
        for (int i =0; i<daysToSchedule; i++){
            Calendar c = Calendar.getInstance();
            Date todayDate = Date.from(Instant.now());
            c.setTime(todayDate);
            c.add(Calendar.DATE,i);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            List<RestrauntSchedule> dayOfWeekSchedules = restraunt.schedules.stream().filter(s -> s.dayOfWeek == dayOfWeek)
                    .collect(Collectors.toList());
            for(RestrauntSchedule schedule : dayOfWeekSchedules){
                //from time -> endtime
                int startTime = schedule.fromTime;
                int endTime = startTime + (slotDuration * 100);
                while (endTime<=schedule.endTime) {
                    RestrauntTables restrauntTable = new RestrauntTables();
                    restrauntTable.tableSlotId = ThreadLocalRandom.current().nextInt(1000);
                    restrauntTable.date = todayDate;
                    restrauntTable.restrauntId = restraunt.id;
                    restrauntTable.noOfTableLeft = restraunt.noOfTable;
                    restrauntTable.fromTime = startTime;
                    restrauntTable.endTime = endTime;
                    restrauntTable.name = restraunt.name;
                    startTime = endTime;
                    endTime = startTime + (slotDuration * 100);
                    restrauntTables.add(restrauntTable);
                }
            }
        }
        return restrauntTables;
    }

}
