package org.esiea.festicore;

import org.esiea.festicore.Enumeration.ArtistName;

import java.util.Map;
import java.util.TreeSet;

public class Festival {
    private String name;
    private TreeSet<Stage> stages;
    private TreeSet<ArtistName> artists;
    private TreeSet<Concert> concerts;
    private Map<String, User> users;
    private Map<String, Reservation> reservations;

    public Festival(String name, TreeSet<Stage> stages, TreeSet<ArtistName> artists, TreeSet<Concert> concerts, Map<String, User> users, Map<String, Reservation> reservations) {
        this.name = name;
        this.stages = stages;
        this.artists = artists;
        this.concerts = concerts;
        this.users = users;
        this.reservations = reservations;
    }

    public Festival() {
        this.name = "FestiCore";
        this.stages = new TreeSet<>();
        this.artists = new TreeSet<>();
        this.concerts = new TreeSet<>();
        this.users = new java.util.HashMap<>();
        this.reservations = new java.util.HashMap<>();
    }

    //All getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Stage> getStages() {
        return stages;
    }

    public void setStages(TreeSet<Stage> stages) {
        this.stages = stages;
    }

    public TreeSet<ArtistName> getArtists() {
        return artists;
    }

    public void setArtists(TreeSet<ArtistName> artists) {
        this.artists = artists;
    }

    public TreeSet<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(TreeSet<Concert> concerts) {
        this.concerts = concerts;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<String, Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Map<String, Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservation findReservation(String code) {
        if (reservations == null) return null;
        return reservations.get(code);
    }

    public void initDefaultProgram() {
        Stage mainStage = new Stage("Main Stage", 10000);
        Stage secondStage = new Stage("Second Stage", 8000);

        this.stages.add(mainStage);
        this.stages.add(secondStage);

        this.concerts.add(new Concert(
                "Concert_Damso",
                ArtistName.Damso,
                mainStage,
                java.time.LocalDateTime.parse("2026-07-15T18:00:00"),
                java.time.Duration.ofMinutes(90)
        ));

        this.concerts.add(new Concert(
                "Concert_Booba",
                ArtistName.Booba,
                secondStage,
                java.time.LocalDateTime.parse("2026-07-15T20:00:00"),
                java.time.Duration.ofMinutes(75)
        ));
    }


    public String showProgram() {
        StringBuilder program = new StringBuilder("Festival " + name + " Program:\n");
        for (Concert concert : concerts) {
            program.append("Concert: ").append(concert.getName())
                   .append(", Artist: ").append(concert.getArtistName())
                   .append(", Stage: ").append(concert.getStage().getName())
                   .append(", Start Time: ").append(concert.getStartDateTime())
                   .append(", Duration: ").append(concert.getDuration().toMinutes()).append(" min\n");
        }
        return program.toString();
    }
}
