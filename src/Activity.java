import java.time.LocalDate;
import Enumaration.ActivityType;

public class Activity extends Reservation{
    private ActivityType activityType;
//    private final Artist artist;
//    private final Duration duration;

    public Activity(String id, float price, LocalDate validityDate, int quota, ActivityType activityType) {
        super(id, price, validityDate, quota);
        this.activityType = activityType;
    }
}
