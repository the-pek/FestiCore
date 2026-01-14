package org.esiea.festicore;

import java.time.Duration;
import java.time.LocalDate;

public class Concert implements Comparable<Concert> {
    private String name;
    private Artist artist;
    private Stage stage;
    private LocalDate StartDateTime;
    private Duration duration;
    private static final java.util.logging.Logger logger = LogManager.getLogger();
    
    public Concert(String name, Artist artist, Stage stage, LocalDate startDateTime, Duration duration) {
        this.name = name;
        this.artist = artist;
        this.stage = stage;
        this.StartDateTime = startDateTime;
        this.duration = duration;
        logger.info("Concert created: " + name + " artist=" + (artist==null?"null":artist.getName()) + " stage=" + (stage==null?"null":stage.getName()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        logger.fine("Concert renamed to: " + name);
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        logger.fine("Concert artist set: " + (artist==null?"null":artist.getName()));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        logger.fine("Concert stage set: " + (stage==null?"null":stage.getName()));
    }

    public LocalDate getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.StartDateTime = startDateTime;
        logger.fine("Concert start time set: " + startDateTime);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        logger.fine("Concert duration set: " + duration);
    }

    //Redifine equals, hashCode, and compareTo for Festival management
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Concert concert = (Concert) obj;
        return name.equals(concert.name) && artist.equals(concert.artist) && stage.equals(concert.stage) && StartDateTime.equals(concert.StartDateTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + artist.hashCode();
        result = 31 * result + stage.hashCode();
        result = 31 * result + StartDateTime.hashCode();
        return result;
    }

    public int compareTo(Concert other) {
        int dateComparison = this.StartDateTime.compareTo(other.StartDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }
        int stageComparison = this.stage.getName().compareTo(other.stage.getName());
        if (stageComparison != 0) {
            return stageComparison;
        }
        return this.artist.getName().compareTo(other.artist.getName());
    }
}