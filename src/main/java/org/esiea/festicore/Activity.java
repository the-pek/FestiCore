package org.esiea.festicore;
import org.esiea.festicore.Enumeration.ActivityType;

import java.time.Duration;
import java.time.LocalDate;


public class Activity extends Reservation{
    private final ActivityType activityType;
    private final Artist artistName;
    private final Duration duration;

    public Activity(String id, float price, LocalDate validityDate, int quota, ActivityType activityType, Artist artist, Duration duration) {
        super(getIdType(activityType), price, validityDate, quota);
        this.activityType = activityType;
        this.artistName = artist;
        this.duration = duration;
    }

    public static String getIdType(ActivityType type) {
        switch (type) {
            case MEET_AND_GREET:
                return "5";
            case MASTERCLASS:
                return "6";
            case BACKSTAGE_TOUR:
                return "7";
            case WORKSHOP:
                return "8";
            default:
                break;
        }
        return null;
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
