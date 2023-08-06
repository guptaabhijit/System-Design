package Models;

import java.util.List;

public class Restaurant {
    public int id;
    public String name;
    public int noOfTable;

    public List<RestrauntSchedule> schedules;
    public List<RestrauntTables> tables;

}
