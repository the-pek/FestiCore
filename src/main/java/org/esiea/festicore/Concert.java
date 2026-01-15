package org.esiea.festicore;

import org.esiea.festicore.Enumeration.ArtistName;
import org.esiea.festicore.service.LogManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Concert implements Comparable<Concert> {
    private String name;
    private ArtistName artistName;
    private Stage stage;
    private LocalDateTime startDateTime;
    private Duration duration;

    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Concert(String name, ArtistName artistName, Stage stage, LocalDateTime startDateTime, Duration duration) {
        this.name = name;
        this.artistName = artistName;
        this.stage = stage;
        this.startDateTime = startDateTime;
        this.duration = duration;
        logger.info("Concert created: " + name + " artist=" + artistName + " stage=" + (stage==null?"null":stage.getName()));
    }

    public Concert() {
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
        logger.fine("Concert artist set: " + artistName);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        logger.fine("Concert stage set: " + (stage==null?"null":stage.getName()));
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Concert other)) return false;
        return Objects.equals(name, other.name)
                && artistName == other.artistName
                && Objects.equals(stage, other.stage)
                && Objects.equals(startDateTime, other.startDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artistName, stage, startDateTime);
    }

    @Override
    public int compareTo(Concert other) {
        if (other == null) return 1;

        int dateComparison = this.startDateTime.compareTo(other.startDateTime);
        if (dateComparison != 0) return dateComparison;

        int stageComparison = this.stage.getName().compareTo(other.stage.getName());
        if (stageComparison != 0) return stageComparison;

        return this.artistName.compareTo(other.artistName);
    }
}
