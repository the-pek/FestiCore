package org.festicore.festicore;

import org.festicore.festicore.Enumeration.TicketType;
import java.time.LocalDate;

import org.festicore.festicore.Exceptions.ReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    @Test
    @DisplayName("T-RES-01")
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
    @DisplayName("T-RES-02")
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
    @DisplayName("T-RES-03")
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
    @DisplayName("T-RES-04")
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