package org.esiea;

import org.esiea.Enumeration.TicketType;
import java.time.LocalDate;

public class Tickets extends Reservation {
    private final TicketType ticketType;

    public Tickets(String id, float price, LocalDate validityDate, int quota, TicketType ticketType) {
        super(id, price, validityDate, quota);
        this.ticketType = ticketType;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    @Override
    public double  calculatePrice() {
        return getPrice();
    }
}
