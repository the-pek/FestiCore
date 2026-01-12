package Execptions;

public class ReservationException extends FestivalException {
    public ReservationException() {
        System.out.println("Unable to save reservation");
    }
}
