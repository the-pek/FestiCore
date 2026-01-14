package org.esiea.festicore.service;

import org.esiea.festicore.*;
import org.esiea.festicore.Exceptions.ReservationException;

public class BookingService {

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
