package org.esiea.festicore;
import org.esiea.festicore.Enumeration.ActivityType;
import org.esiea.festicore.Enumeration.ArtistName;
import org.esiea.festicore.service.LogManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Activity extends Reservation{
    private ActivityType activityType;
    private ArtistName artistName;
    private Duration duration;
    private LocalDateTime startTime;
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Activity(String id, float price, LocalDate validityDate, int quota, ActivityType activityType, ArtistName artistName, Duration duration, LocalDateTime startTime) {
        super(id, price, validityDate, quota);
        this.activityType = activityType;
        this.artistName = artistName;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Activity() {

    }

    public ActivityType getActivityType() {
        return activityType;
    }
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
    public ArtistName getArtistName() { return artistName; }
    public void setArtistName(ArtistName artistName) { this.artistName = artistName; }
    public Duration getDuration() {
        return duration;
    }
    public void setDuration(Duration duration) {
        this.duration = duration;
    }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    @Override
    public double  calculatePrice() {
        logger.fine("Calculating price for activity: " + getId());
        return getPrice();
    }
    @Override
    public Reservation copyForHistory() {
        return new Activity( getId(), getPrice(), getValidityDate(), 1, getActivityType(), getArtistName(), getDuration(), getStartTime());
    }


}
