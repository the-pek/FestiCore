package org.esiea.festicore;

import org.esiea.festicore.Exceptions.ReservationException;

import java.time.LocalDate;

public abstract class Reservation {
    private final String id;
    private float price;
    private LocalDate validityDate;
    private int quota;

    public Reservation(String id, float price, LocalDate validityDate, int quota) {
        this.id = id;
        this.price = price;
        this.validityDate = validityDate;
        this.quota = quota;
    }

    public String getId() {
        return id;
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
            throw new ReservationException("Unable to complete reservation");
        }
        quota--;
    }

    public abstract double calculatePrice();

    //Redifine 
}
