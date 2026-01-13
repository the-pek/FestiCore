package org.esiea.festicore;

public class Artist implements Comparable<Artist> {
    private String name;

    public Artist(String name) {
        this.name = name;
    }

    //All getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Redefine equals, hashCode, and compareTo for Festival management
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Artist artist = (Artist) obj;
        return name.equals(artist.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int compareTo(Artist other) {
        return this.name.compareTo(other.name);
    }
}
