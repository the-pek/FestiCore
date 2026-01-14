package org.esiea.festicore;

public class Stage implements Comparable<Stage> {
    private String name;
    private int capacity;

    public Stage(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    //All getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //Redefine equals, hashCode, and compareTo for Festival management
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Stage stage = (Stage) obj;
        return name.equals(stage.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int compareTo(Stage other) {
        return this.name.compareTo(other.name);
    }
}