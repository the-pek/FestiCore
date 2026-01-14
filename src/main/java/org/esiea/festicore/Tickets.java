package org.esiea.festicore;

import org.esiea.festicore.Enumeration.TicketType;
import java.time.LocalDate;

public class Tickets extends Reservation {
    private TicketType ticketType;
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Tickets(String id, float price, LocalDate validityDate, int quota, TicketType ticketType) {
        super(id, price, validityDate, quota);
        this.ticketType = ticketType;
        logger.info("Ticket created: " + id + " type=" + ticketType);
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
        logger.fine("Calculating ticket price for: " + getId());
        return getPrice();
    }
}
