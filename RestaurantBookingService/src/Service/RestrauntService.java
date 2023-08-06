package Service;

import Models.Restaurant;
import Models.RestrauntSchedule;
import Models.RestrauntTables;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class RestrauntService {

    final Map<Integer, Restaurant> restraunts;

    final RestrauntTableScheduler restrauntTableScheduler;

    List<RestrauntTables> restrauntTables;

    public RestrauntService(RestrauntTableScheduler restrauntTableScheduler) {
        this.restrauntTableScheduler = restrauntTableScheduler;
        this.restraunts = new HashMap<>();
        restrauntTables = new ArrayList<>();
    }

    public Restaurant registerRestraunt(final String name, final int tables, final List<RestrauntSchedule> schedules){

        Restaurant restraunt = new Restaurant();
        restraunt.id = ThreadLocalRandom.current().nextInt(100,1000);
        restraunt.name = name;
        restraunt.noOfTable = tables;
        restraunt.schedules = schedules;
        restraunts.put(restraunt.id,restraunt);
        restrauntTables.addAll(restrauntTableScheduler.createScheduleForARestraunt(restraunt));
        return restraunt;
    }

    List<RestrauntTables> searchRestraunt(String name, Date on, final int fromTime, final int endTime){

        return restrauntTables.stream().filter( rt -> rt.name.contains(name))
                .filter(st -> st.date.equals(on))
                .filter(st -> st.fromTime >= fromTime)
                .filter(st -> st.endTime <= endTime).collect(Collectors.toList());

    }

    List<RestrauntTables> searchRestraunt(final int restrauntId, final Date on, final int fromTime, final int endTime){

        List<RestrauntTables> result = restrauntTables.stream()
                .filter(st -> st.restrauntId == restrauntId)
                //.filter(st -> st.date.getDate() == on.getDate())
                .filter(st -> st.fromTime >= fromTime)
                .filter(st -> st.endTime <= endTime).collect(Collectors.toList());
        return result;
    }
}
