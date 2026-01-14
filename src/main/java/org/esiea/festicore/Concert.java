package org.esiea.festicore;

import org.esiea.festicore.Enumeration.ArtistName;

import java.time.Duration;
import java.time.LocalDate;

public class Concert implements Comparable<Concert> {
    private String name;
    private ArtistName artistName;
    private Stage stage;
    private LocalDate startDateTime;
    private Duration duration;
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Concert(String name, ArtistName artistName, Stage stage, LocalDate startDateTime, Duration duration) {
        this.name = name;
        this.artistName = artistName;
        this.stage = stage;
        this.startDateTime = startDateTime;
        this.duration = duration;
        logger.info("Concert created: " + name + " artist=" + getArtistName() + " stage=" + (stage==null?"null":stage.getName()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        logger.fine("Concert renamed to: " + name);
    }

    public ArtistName getArtistName() {
        return artistName;
    }

    public void setArtistName(ArtistName artistName) {
        this.artistName = artistName;
        logger.fine("Concert artist set: " + getArtistName());
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        logger.fine("Concert stage set: " + (stage==null?"null":stage.getName()));
    }

    public LocalDate getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.startDateTime = startDateTime;
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
        return name.equals(concert.name) && artistName.equals(concert.artistName) && stage.equals(concert.stage) && startDateTime.equals(concert.startDateTime);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + artistName.hashCode();
        result = 31 * result + stage.hashCode();
        result = 31 * result + startDateTime.hashCode();
        return result;
    }

    public int compareTo(Concert other) {
        int dateComparison = this.startDateTime.compareTo(other.startDateTime);
        if (dateComparison != 0) {
            return dateComparison;
        }
        int stageComparison = this.stage.getName().compareTo(other.stage.getName());
        if (stageComparison != 0) {
            return stageComparison;
        }
        return this.getArtistName().compareTo(other.getArtistName());
    }
}