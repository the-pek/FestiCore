package org.esiea.festicore;
import org.esiea.festicore.Enumeration.ActivityType;
import java.time.Duration;
import java.time.LocalDate;


public class Activity extends Reservation{
    private final ActivityType activityType;
    private final Artist artistName;
    private final Duration duration;
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Activity(String id, float price, LocalDate validityDate, int quota, ActivityType activityType, Artist artist, Duration duration) {
        super(id, price, validityDate, quota);
        this.activityType = activityType;
        this.artistName = artist;
        this.duration = duration;
        logger.info("Activity created: " + id + " type=" + activityType + " artist=" + (artist==null?"null":artist.getName()));
    }

    public ActivityType getActivityType() {
        return activityType;
    }
    public Artist getArtist() {
        return artistName;
    }
    public Duration getDuration() {
        return duration;
    }

    @Override
    public double  calculatePrice() {
        logger.fine("Calculating price for activity: " + getId());
        return getPrice();
    }
}
