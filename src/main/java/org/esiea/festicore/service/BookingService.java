package org.esiea.festicore.service;

import org.esiea.festicore.*;
import org.esiea.festicore.Exceptions.ReservationException;
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
}
