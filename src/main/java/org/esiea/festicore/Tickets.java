package org.esiea.festicore;

import org.esiea.festicore.Enumeration.TicketType;
import java.time.LocalDate;

public class Tickets extends Reservation {
    private TicketType ticketType;

    public Tickets(String id, float price, LocalDate validityDate, int quota, TicketType ticketType) {
        super(id, price, validityDate, quota);
        this.ticketType = ticketType;
    }

    public Tickets() {

    }
  
    public TicketType getTicketType() {
        return ticketType;
    }
    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public double  calculatePrice() {
        return getPrice();
    }
}
