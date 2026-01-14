package org.esiea.festicore.service;

import org.esiea.festicore.*;
import org.esiea.festicore.Enumeration.ActivityType;
import org.esiea.festicore.Enumeration.PassType;
import org.esiea.festicore.Enumeration.TicketType;
import org.esiea.festicore.Exceptions.ReservationException;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class BookingService {

    private Map<Object, Integer> inventory;

    public BookingService() {
        this.inventory = new HashMap<>();
    }

    public void bookInventory (Object type) {
        if(!inventory.containsKey(type) || inventory.get(type) == 0) {
            System.out.println("No inventory available for type: " + type);
        }
        
        inventory.put(type, inventory.get(type) - 1);
    }

    public Reservation book(User user, Reservation reservation)
            throws ReservationException {

        if (user == null) {
            throw new ReservationException("User not logged in");
        }

        if (!reservation.inSale()) {
            throw new ReservationException("Reservation not available");
        }

        reservation.decrementerQuota();

        user.addReservation(reservation);

        return reservation;
    }

    public Map<String, Reservation> createDefaultReservations() {
        Map<String, Reservation> catalog = new HashMap<>();

        LocalDate d = LocalDate.parse("2026-07-15");

        catalog.put("Tickets simple", new Tickets("T_Day", 80f, d, 3, TicketType.day));
        catalog.put("Tickets 3 jours",  new Tickets("T_3Day",  240f, d, 3, TicketType.Three_day));

        catalog.put("Pass Classic", new Pass("P_Classic", 100f, d, 3, PassType.classic));
        catalog.put("Pass VIP", new Pass("P_VIP", 250f, d, 3, PassType.VIP));

        //catalog.put("A_Meet", new Activity("A_Meet", 250f, d, 3, ActivityType.MEET_AND_GREET));

        return catalog;
    }

}
