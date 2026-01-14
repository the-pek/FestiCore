package org.esiea.festicore;

import org.esiea.festicore.Exceptions.ReservationException;

import java.time.LocalDate;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "kind")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tickets.class, name = "TICKET"),
        @JsonSubTypes.Type(value = Pass.class, name = "PASS"),
        @JsonSubTypes.Type(value = Activity.class, name = "ACTIVITY")
})

public abstract class Reservation {
    private String id;
    private float price;
    private LocalDate validityDate;
    private int quota;
    Logger logger = LogManager.getLogger();

    public Reservation(String id, float price, LocalDate validityDate, int quota) {
        this.id = id;
        this.price = price;
        this.validityDate = validityDate;
        this.quota = quota;
    }

    public Reservation() {

    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }
    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public int getQuota() {
        return quota;
    }
    public void setQuota(int quota) {
        this.quota = quota;
    }

    public boolean inSale() {
        return quota > 0;
    }

    public void decrementerQuota() throws ReservationException {
        if (quota <= 0) {
            logger.warning("Attempt to decrement quota but none available for reservation: " + id);
            throw new ReservationException("Unable to complete reservation");
        }
        quota--;
        logger.fine("Quota decremented for reservation " + id + ". New quota: " + quota);
    }

    public abstract double calculatePrice();
}
