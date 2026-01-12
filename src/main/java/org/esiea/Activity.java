package org.esiea;
import org.esiea.Enumeration.ActivityType;
import java.time.Duration;
import java.time.LocalDate;


public class Activity extends Reservation{
    private final ActivityType activityType;
    private final Artist artistName;
    private final Duration duration;

    public Activity(String id, float price, LocalDate validityDate, int quota, ActivityType activityType, Artist artist, Duration duration) {
        super(id, price, validityDate, quota);
        this.activityType = activityType;
        this.artistName = artist;
        this.duration = duration;
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
        return getPrice();
    }
}
