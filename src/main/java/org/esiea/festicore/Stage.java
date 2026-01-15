package org.esiea.festicore;

import org.esiea.festicore.service.LogManager;

public class Stage implements Comparable<Stage> {
    private String name;
    private int capacity;
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Stage(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        logger.info("Stage created: " + name + " capacity=" + capacity);
    }

    //All getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        logger.fine("Stage renamed to: " + name);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        logger.fine("Stage capacity changed: " + capacity + " for " + name);
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