package org.festicore.festicore.service;

import org.esiea.festicore.*;
import org.esiea.festicore.Enumeration.*;
import org.festicore.festicore.*;
import org.festicore.festicore.Enumeration.ActivityType;
import org.festicore.festicore.Enumeration.ArtistName;
import org.festicore.festicore.Enumeration.PassType;
import org.festicore.festicore.Enumeration.TicketType;
import org.festicore.festicore.Exceptions.ReservationException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BookingService {
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Reservation book(User user, Reservation reservation) throws ReservationException {
        logger.info("Attempting booking: user=" + (user==null?"null":user.getEmail()) + ", reservation=" + (reservation==null?"null":reservation.getId()));

        if (user == null) {
            logger.warning("Booking failed: user not logged in");
            throw new ReservationException("User not logged in");
        }
        if (reservation == null) {
            throw new ReservationException("Reservation not found");
        }
        if (!reservation.inSale()) {
            logger.warning("Booking failed: reservation not available: " + reservation.getId());
            throw new ReservationException("Reservation not available");
        }

        reservation.decrementerQuota();
        user.addReservation(reservation.copyForHistory());
        logger.info("Booking successful: user=" + user.getName() + ", reservation=" + reservation.getId());

        return reservation;
    }

    public Reservation bookById(User user, Festival festival, String code) throws ReservationException {
        if (festival == null) throw new ReservationException("Festival not initialized");
        if (code == null || code.isBlank()) throw new ReservationException("Invalid reservation id");

        Reservation r = festival.findReservation(code.trim());
        return book(user, r);
    }

    public Map<String, Reservation> createDefaultReservations() {
        Map<String, Reservation> catalog = new HashMap<>();

        LocalDate d = LocalDate.parse("2026-07-15");

        // Tickets
        catalog.put("Ticket_Day", new Tickets("T_Day", 80f, d, 3, TicketType.day));
        catalog.put("Ticket_3Day",  new Tickets("T_3Day", 240f, d, 3, TicketType.Three_day));

        // Pass
        catalog.put("Pass_Classic", new Pass("P_Classic", 100f, d, 3, PassType.classic));
        catalog.put("Pass_VIP", new Pass("P_VIP", 250f, d, 3, PassType.VIP));

        // Activities
        // IMPORTANT: adapt arguments to your Activity constructor signature.
        // If your Activity uses startTime (LocalDateTime), keep it. Otherwise remove it.
        LocalDateTime t1 = LocalDateTime.parse("2026-07-15T10:00:00");
        LocalDateTime t2 = LocalDateTime.parse("2026-07-15T14:00:00");

        catalog.put("Activity_MEET_DAMSO",
                new Activity("A_MEET_DAMSO", 250f, d, 3,
                        ActivityType.MEET_AND_GREET,
                        ArtistName.Damso,
                        Duration.ofMinutes(60),
                        t1
                )
        );

        catalog.put("Activity_MasterC_BOOBA",
                new Activity("A_MASTERCLASS_BOOBA", 180f, d, 3,
                        ActivityType.MASTERCLASS,
                        ArtistName.Booba,
                        Duration.ofMinutes(90),
                        t2
                )
        );

        return catalog;
    }

    public void initOrMergeCatalog(Festival festival) {
        if (festival == null) return;

        // Festival must have a non-null reservations map
        if (festival.getReservations() == null) {
            festival.setReservations(new HashMap<>());
        }

        Map<String, Reservation> current = festival.getReservations();
        Map<String, Reservation> defaults = createDefaultReservations();

        // Merge: keep existing, add missing
        for (Map.Entry<String, Reservation> e : defaults.entrySet()) {
            current.putIfAbsent(e.getKey(), e.getValue());
        }
    }

    public void printCatalog(Festival festival) {
        if (festival == null || festival.getReservations() == null) {
            System.out.println("No catalog loaded.");
            return;
        }
        for (Map.Entry<String, Reservation> e : festival.getReservations().entrySet()) {
            Reservation r = e.getValue();
            System.out.println("- Code: " + e.getKey() + " | price=" + r.calculatePrice() );
        }
    }
}
