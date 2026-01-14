package org.esiea.festicore;
import org.esiea.festicore.Exceptions.ReservationException;
import org.esiea.festicore.service.BookingService;

import java.util.*;
import java.time.LocalDate;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private LocalDate registrationDate;
    private List<Reservation> history;

    public User(String id, String name, String email, String phone, String password, LocalDate registrationDate, List<Reservation> history) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.registrationDate = registrationDate;
        this.history = history;
    }

    public User() {
    }


    // Getters and setters for each field
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Reservation> getHistory() {
        return history;
    }

    public void setHistory(List<Reservation> history) {
        this.history = history;
    }

    //Create method to add a login 
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }


    //Create all methods we need to register a new user 
    //Method to create a Id uniqaue for each user
    public static String generateUniqueId() {
        return "USER-" + java.util.UUID.randomUUID().toString();
    }

    //Method to verify if email is valid
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    //Method to verify if phone is valid for a french number
    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^(\\+33|0)[1-9](\\d{2}){4}$";
        return phone.matches(phoneRegex);
    }

    // Method to register a new user
    public static User register(String name, String email, String phone, String password) {
        // Validate email and phone
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email invalide.");
        }
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Numéro de téléphone invalide.");
        }
        List<Reservation> history = new ArrayList<>();
        ; // Initialize history as null
        User newUser = new User(generateUniqueId(), name, email, phone, password, LocalDate.now(), history);
        return newUser;
    }

    //Method to make all account operations a user needs
    public void account(Scanner scanner, Festival festival, BookingService bookingService) {
        boolean exit = false;
        String command;

        System.out.println("---Welcome to your account " + name + "---");
        System.out.println(" ");
        System.out.println("Tap '-h' to show you the actions you can perform");
        while (!exit) {
            command = scanner.nextLine().trim();
            switch (command) {

                //Command to see all reservation history
                case "-a":
                    if (history == null || history.isEmpty()) {
                        System.out.println("No reservation history available.");
                    } else {
                        for (Reservation reservation : history) {
                            System.out.println(reservation);
                        }
                    }
                    break;
                //Command to search a reservation by its id
                case "-r":
                    System.out.println("Enter reservation ID to find:");
                    String reservationId = scanner.nextLine().trim();
                    Reservation foundReservation = festival.findReservation(reservationId);
                    if(foundReservation == null) {
                        System.out.println("Reservation not found.");
                    } else {
                        System.out.println(foundReservation);
                    }
                    break;

                //Command to buy a ticket, pass, activity
                case "-b":
                    System.out.println("Enter reservation ID to book:");
                    String resId = scanner.nextLine().trim();

                    Reservation reservation = festival.findReservation(resId);
                    if (reservation == null) {
                        System.out.println("Reservation not found.");
                        break;
                    }
                    if (this.hasReservation(resId)) {
                        System.out.println("You already booked this reservation.");
                        break;
                    }

                    try {
                        System.out.println("Price: " + reservation.calculatePrice() + "€");
                        System.out.print("Enter card number : ");
                        String card = scanner.nextLine().trim();
                        if (card.isEmpty()) {
                            System.out.println("Payment cancelled.");
                            break;
                        }

                        bookingService.book(this, reservation);
                        System.out.println(
                                "Reservation successful. Price: "
                                        + reservation.calculatePrice() + "€"
                        );
                    } catch (ReservationException e) {
                        System.out.println("Booking failed: " + e.getMessage());
                    }
                    break;
                //Command to show program
                case "-p":
                    System.out.println(festival.showProgram());
                    break;
                //Command to display help
                case "-h":
                    this.displayHelp();
                    break;
                //Command to logout
                case "-q":
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Unknown command. For help, type -h.");
            }
        }
    }

    public void displayHelp() {
        System.out.println("-a : Show reservation history");
        System.out.println("-r : Find a reservation by ID");
        System.out.println("-b : Buy a reservation");
        System.out.println("-p : Show festival program");
        System.out.println("-q : Logout");
    }


    public void addReservation(Reservation reservation) {
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(reservation);
    }
    public boolean hasReservation(String reservationId) {
        if (history == null) return false;
        return history.stream()
                .anyMatch(r -> r.getId().equals(reservationId));
    }
}