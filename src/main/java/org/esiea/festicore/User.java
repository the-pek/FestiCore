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
    private String card;
    private String password;
    private LocalDate registrationDate;
    private List<Reservation> history;
    private static final java.util.logging.Logger logger = LogManager.getLogger();

    public User(String id, String name, String email, String phone, String password, LocalDate registrationDate, List<Reservation> history) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.card = null;
        this.password = password;
        this.registrationDate = registrationDate;
        this.history = history;
    }   

    // Default constructor required by Jackson for deserialization
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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
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
        return this.email != null
                && this.password != null
                && this.email.equals(email)
                && this.password.equals(User.hashPassword(password));
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

    //Method to crypt password 
    public static String hashPassword(String password) {
        return Integer.toString(89 * password.hashCode() + 20);
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
        User newUser = new User(generateUniqueId(), name, email, phone, hashPassword(password), LocalDate.now(), history);
        return newUser;
    }

    //Method to buy a reservation and add it to the history
    public void buyReservation(Scanner scanner, Festival festival) {
        System.out.println("Enter reservation Id to buy:");
        logger.info("Prompting user to enter reservation id to buy.");
        String reservationId = scanner.nextLine().trim();

        Reservation reservation = festival.findReservation(reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            logger.warning("Reservation not found: " + reservationId);
            return;
        }

        try {
            if (history == null) {
                history = new ArrayList<>();
            }

            System.out.println("Reservation price: " + reservation.calculatePrice());
            logger.info("Reservation price displayed: " + reservation.calculatePrice() + " for reservation " + reservationId);
            if(this.card == null) {
                System.out.println("Enter your card number to proceed with payment:");
                logger.info("Prompting for card number for reservation: " + reservationId);
                String cardNumber = scanner.nextLine().trim();
                System.out.println("Do you want to save this card for future purchases? (yes/no)");
                String saveCardResponse = scanner.nextLine().trim().toLowerCase();
                if (saveCardResponse.equals("yes")) {
                this.setCard(cardNumber);
                System.out.println("Card saved successfully.");
                logger.info("Card saved for user: " + this.email);
                }
            }

            System.out.println("Did you want to proceed with the payment? (yes/no)");
            logger.info("Asking user to confirm payment for reservation: " + reservationId);
            String paymentResponse = scanner.nextLine().trim().toLowerCase();
            if (!paymentResponse.equals("yes")) {
                System.out.println("Payment cancelled.");
                logger.info("Payment cancelled by user for reservation: " + reservationId);
                return;
            }
            
            System.out.println("Processing payment...");// Simulate payment processing
            logger.info("Processing payment for reservation: " + reservationId);
            history.add(reservation);
            reservation.decrementerQuota();
            System.out.println("Reservation purchased successfully!");
            logger.info("Reservation purchased: " + reservationId + " by user: " + this.email);
        } catch (Exception e) {
            System.out.println("Error purchasing reservation: " + e.getMessage());
            logger.severe("Error purchasing reservation for user " + this.email + ": " + e.getMessage());
        }
    }

    //Method to make all account operations a user needs
    public void account(Scanner scanner, Festival festival, BookingService bookingService) {
        boolean exit = false;
        String command;

        System.out.println("---Welcome to your account " + name + "---");
        logger.info("User accessed account: " + name);
        System.out.println("Select an action to perform :");
            System.out.println("1.Show all hitory -a");
            System.out.println("2.Search reservation -r");
            System.out.println("3.Buy reservation -b");
            System.out.println("4.Show program -p");
            System.out.println("5.Quit -q");
            System.out.println("6.Help -h");
        while (!exit) {
            command = scanner.nextLine().trim();
            switch (command) {

                //Command to see all reservation history
                case "-a":
                    if (history == null || history.isEmpty()) {
                        System.out.println("No reservation history available.");
                        logger.info("No reservation history available for user: " + name);
                    } else {
                        for (Reservation reservation : history) {
                            System.out.println(reservation);
                        }
                    }
                    break;
                //Command to search a reservation by its id
                case "-r":
                    System.out.println("Enter reservation ID to find:");
                    logger.info("Prompting user to enter reservation ID to find.");
                    String reservationId = scanner.nextLine().trim();
                    Reservation foundReservation = festival.findReservation(reservationId);
                    if(foundReservation == null) {
                        System.out.println("Reservation not found.");
                        logger.warning("Reservation not found during search: " + reservationId);
                    } else {
                        System.out.println(foundReservation);
                        logger.info("Found reservation: " + reservationId);
                    }
                    break;

                //Command to buy a ticket, pass, activity
                case "-b":
                    System.out.println("Enter reservation ID to book:");
                    logger.info("Prompting user to enter reservation ID to book.");
                    String resId = scanner.nextLine().trim();

                    Reservation reservation = festival.findReservation(resId);

                    if (reservation == null) {
                        System.out.println("Reservation not found.");
                        logger.warning("Attempted booking for non-existent reservation: " + resId);
                        break;
                    }

                    if (this.hasReservation(resId)) {
                        System.out.println("You already booked this reservation.");
                        logger.info("User attempted to re-book reservation already owned: " + resId + " by user " + this.email);
                        break;
                    }

                    try {
                        bookingService.book(this, reservation);
                        System.out.println(
                            "Reservation successful. Price: "
                                + reservation.calculatePrice() + "€"
                        );
                        logger.info("Reservation booked: " + resId + " for user: " + this.email + ". Price: " + reservation.calculatePrice());
                    } catch (ReservationException e) {
                        System.out.println("Booking failed: " + e.getMessage());
                        logger.severe("Booking failed for user " + this.email + ": " + e.getMessage());
                    }
                    break;
                //Command to show program
                case "-p":
                    festival.showProgram();
                    break;
                //Command to display help
                case "-h":
                    this.displayHelp();
                    break;
                //Command to logout
                case "-q":
                    exit = true;
                    System.out.println("Logging out...");
                    logger.info("User logging out: " + name);
                    break;
                default:
                    System.out.println("Unknown command. For help, type -h.");
            }
        }
    }

    public void displayHelp() {
        System.out.println("Account Help Menu:");   
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