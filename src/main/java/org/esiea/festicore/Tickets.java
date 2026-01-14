package org.esiea.festicore;

import org.esiea.festicore.Enumeration.TicketType;
import java.time.LocalDate;

public class Tickets extends Reservation {
    private final TicketType ticketType;

    public Tickets(String id, float price, LocalDate validityDate, int quota, TicketType ticketType) {
        super(getIdType(ticketType), price, validityDate, quota);
        this.ticketType = ticketType;
    }

    public static String getIdType(TicketType type) {
        switch (type) {
            case day:
                return "1";
            case Three_day:
                return "2";
            default:
                break;
        }
        return null;
    }   

    public TicketType getTicketType() {
        return ticketType;
    }

    @Override
    public double  calculatePrice() {
        return getPrice();
    }
}
