package org.esiea.festicore;
import org.esiea.festicore.Enumeration.ActivityType;

import java.time.Duration;
import java.time.LocalDate;


public class Activity extends Reservation{
    private ActivityType activityType;
    private Artist artistName;
    private Duration duration;

    public Activity(String id, float price, LocalDate validityDate, int quota, ActivityType activityType, Artist artist, Duration duration) {
        super(id, price, validityDate, quota);
        this.activityType = activityType;
        this.artistName = artist;
        this.duration = duration;
    }

    public Activity() {

    }

    public ActivityType getActivityType() {
        return activityType;
    }
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
    public Artist getArtist() {
        return artistName;
    }
    public Duration getDuration() {
        return duration;
    }

    @Override
    public double  calculatePrice() {
        return getPrice();
    }
}
