package org.esiea.festicore;
import org.esiea.festicore.Exceptions.ReservationException;
import org.esiea.festicore.service.BookingService;
import org.esiea.festicore.service.JsonDataManager;
import org.esiea.festicore.service.LogManager;

import java.io.IOException;
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

    //Method to make all account operations a user needs
    public void account(Scanner scanner, Festival festival, BookingService bookingService, JsonDataManager storageData) {
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
                    System.out.println("Enter reservation code to find (example: Ticket_Day):");
                    String codeToFind = scanner.nextLine().trim();

                    Reservation found = festival.findReservation(codeToFind);
                    if (found == null) {
                        System.out.println("Reservation not found.");
                    } else {
                        System.out.println("Found: code=" + codeToFind
                                + " | id=" + found.getId()
                                + " | price=" + found.calculatePrice());
                    }
                    break;

                //Command to buy a ticket, pass, activity
                case "-b":
                    bookingService.printCatalog(festival);
                    System.out.println("Enter the reservation code to book:");
                    String code = scanner.nextLine().trim();

                    Reservation reservationToBook = festival.findReservation(code);
                    if (reservationToBook == null) {
                        System.out.println("Reservation not found.");
                        break;
                    }

                    if (this.hasReservation(reservationToBook.getId())) {
                        System.out.println("You already booked this reservation.");
                        break;
                    }

                    try {
                        System.out.println("Price: " + reservationToBook.calculatePrice() + "€");

                        String cardToUse = null;

                        // If a card is already saved, let user choose
                        if (this.card != null && !this.card.isBlank()) {
                            System.out.println("A card is already saved.");
                            System.out.print("Use saved card? (yes/no): ");
                            String useSaved = scanner.nextLine().trim().toLowerCase();

                            if (useSaved.equals("yes")) {
                                cardToUse = getCard();
                            }
                        }

                        // If no card chosen yet, ask for a new one
                        if (cardToUse == null) {
                            System.out.print("Enter card number: ");
                            String enteredCard = scanner.nextLine().trim();
                            if (enteredCard.isEmpty()) {
                                System.out.println("Payment cancelled.");
                                break;
                            }
                            cardToUse = enteredCard;

                            System.out.print("Save this card for future purchases? (yes/no): ");
                            String save = scanner.nextLine().trim().toLowerCase();
                            if (save.equals("yes")) {
                                this.setCard(cardToUse);
                                System.out.println("Card saved.");
                            }
                        }

                        // Optional: confirm payment
                        System.out.print("Proceed with payment? (yes/no): ");
                        String confirm = scanner.nextLine().trim().toLowerCase();
                        if (!confirm.equals("yes")) {
                            System.out.println("Payment cancelled.");
                            break;
                        }

                        System.out.println("Processing payment with card: " + cardToUse + " ...");

                        bookingService.book(this, reservationToBook);
                        storageData.saveReservations(festival.getReservations());

                        System.out.println("Reservation successful. Price: " + reservationToBook.calculatePrice() + "€");
                        logger.info("Reservation booked: " + code + " for user: " + this.name);

                    } catch (ReservationException e) {
                        System.out.println("Booking failed: " + e.getMessage());
                        logger.severe("Booking failed for user " + this.email + ": " + e.getMessage());
                    } catch (IOException e) {
                        System.out.println("Booking ok, but failed to save stock: " + e.getMessage());
                        logger.severe("Stock save failed after booking. code=" + code + " user=" + this.email + " err=" + e.getMessage());
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
        System.out.println("-r : Find a reservation");
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