package org.esiea.festicore;

import org.esiea.festicore.Enumeration.TicketType;
import java.time.LocalDate;

import org.esiea.festicore.Exceptions.ReservationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ReservationTest {
    @Test
    void calculatePriceTest() {
        Reservation r = new Tickets(
                "T1",
                80,
                LocalDate.parse("2026-07-15"),
                10,
                TicketType.day
        );
        assertEquals(80.0, r.calculatePrice());
    }

    @Test
    void reservationQuotaNotNull() {
        Reservation r = new Tickets(
                "T1",
                80f,
                LocalDate.parse("2026-07-15"),
                10,
                TicketType.day
        );
        assertTrue(r.inSale());
    }

    @Test
    void reservationQuotaNull() {
        Reservation r = new Tickets(
                "T2",
                80f,
                LocalDate.parse("2026-07-15"),
                0,
                TicketType.Three_day
        );

        assertFalse(r.inSale());
    }

    @Test
    void reservationDecrementationQuota() throws ReservationException {
        Reservation ticket = new Tickets(
                "T3",
                80f,
                LocalDate.parse("2026-07-15"),
                2,
                TicketType.day
        );

        ticket.decrementerQuota();
        assertEquals(1, ticket.getQuota());
    }

    @Test
    void reservationDecrementationQuotaZero(){
        Reservation ticket = new Tickets(
                "T4",
                80f,
                LocalDate.parse("2026-07-15"),
                0,
                TicketType.day
        );

        assertThrows(
                ReservationException.class,
                ticket::decrementerQuota
        );
    }
}
