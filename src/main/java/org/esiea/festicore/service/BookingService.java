package org.esiea.festicore.service;

import org.esiea.festicore.*;
import org.esiea.festicore.Exceptions.ReservationException;

public class BookingService {
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public Reservation book(User user, Reservation reservation)
            throws ReservationException {

        logger.info("Attempting booking: user=" + (user==null?"null":user.getEmail()) + ", reservation=" + (reservation==null?"null":reservation.getId()));

        if (user == null) {
            logger.warning("Booking failed: user not logged in");
            throw new ReservationException("User not logged in");
        }

        if (!reservation.inSale()) {
            logger.warning("Booking failed: reservation not available: " + reservation.getId());
            throw new ReservationException("Reservation not available");
        }

        reservation.decrementerQuota();

        user.addReservation(reservation);
        logger.info("Booking successful: user=" + user.getEmail() + ", reservation=" + reservation.getId());

        return reservation;
    }
}
