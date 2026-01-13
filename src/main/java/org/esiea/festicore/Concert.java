package org.esiea.festicore;

import java.time.Duration;
import java.time.LocalDate;

public class Concert extends Artist {
    private Artist artist;
    private Stage stage;
    private LocalDate StartDateTime;
    private Duration duration;
    
    public Concert(String name, Artist artist, Stage stage, LocalDate startDateTime, Duration duration) {
        super(name);
        this.artist = artist;
        this.stage = stage;
        this.StartDateTime = startDateTime;
        this.duration = duration;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public LocalDate getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(LocalDate startDateTime) {
        this.StartDateTime = startDateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
