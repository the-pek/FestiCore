package org.esiea.festicore;

import java.util.Map;
import java.util.TreeSet;

public class Festival {
    private String name;
    private TreeSet<Stage> stages;
    private TreeSet<Artist> artists;
    private TreeSet<Concert> concerts;
    private Map<String, User> users;
    private Map<String, Reservation> reservations;

    public Festival(String name, TreeSet<Stage> stages, TreeSet<Artist> artists, TreeSet<Concert> concerts, Map<String, User> users, Map<String, Reservation> reservations) {
        this.name = name;
        this.stages = stages;
        this.artists = artists;
        this.concerts = concerts;
        this.users = users;
        this.reservations = reservations;
    }
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

    public TreeSet<Artist> getArtists() {
        return artists;
    }

    public void setArtists(TreeSet<Artist> artists) {
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

    
}
