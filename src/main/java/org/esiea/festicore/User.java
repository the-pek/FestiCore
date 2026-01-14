package org.esiea.festicore;
import java.util.*;
import java.time.LocalDate;

public class User extends Main  {
    private String Id;
    private String Name;
    private String Email;
    private String Phone;
    private String card;
    private String Password;
    private LocalDate registrationDate;
    private List<Reservation> history;

    public User(String id, String name, String email, String phone, String password, LocalDate registrationDate, List<Reservation> history) {
        this.Id = id;
        this.Name = name;
        this.Email = email;
        this.Phone = phone;
        this.card = null;
        this.Password = password;
        this.registrationDate = registrationDate;
        this.history = history;
    }   

    // Null constructor: initialize all fields to null
    public User() {
        this.Id = null;
        this.Name = null;
        this.Email = null;
        this.Phone = null;
        this.card = null;
        this.Password = null;
        this.registrationDate = null;
        this.history = null;
    
    }

    // Getters and setters for each field
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
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
        return this.Email.equals(email) && this.Password.equals(password);
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
        List<Reservation> history = null; // Initialize history as null
        User newUser = new User(generateUniqueId(), name, email, phone, password, LocalDate.now(), history);
        return newUser;
    }

    
    //Method to buy a reservation and add it to the history
    public void buyReservation(Scanner scanner, Festival festival) {
        System.out.println("Enter reservation Id to buy:");
        String reservationId = scanner.nextLine().trim();

        Reservation reservation = festival.findReservation(reservationId);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        try {
            if (history == null) {
                history = new ArrayList<>();
            }

            System.out.println("Reservation price: " + reservation.calculatePrice());
            if(this.card == null) {
                System.out.println("Enter your card number to proceed with payment:");
                String cardNumber = scanner.nextLine().trim();
                System.out.println("Do you want to save this card for future purchases? (yes/no)");
                String saveCardResponse = scanner.nextLine().trim().toLowerCase();
                if (saveCardResponse.equals("yes")) {
                this.setCard(cardNumber);
                System.out.println("Card saved successfully.");
                }
            }

            System.out.println("Did you want to proceed with the payment? (yes/no)");
            String paymentResponse = scanner.nextLine().trim().toLowerCase();
            if (!paymentResponse.equals("yes")) {
                System.out.println("Payment cancelled.");
                return;
            }
            
            System.out.println("Processing payment...");// Simulate payment processing
            history.add(reservation);
            reservation.decrementerQuota();
            System.out.println("Reservation purchased successfully!");
        } catch (Exception e) {
            System.out.println("Error purchasing reservation: " + e.getMessage());
        }
    }

    //Method to make all account operations a user needs 
    public void account(Scanner scanner, Festival festival) {
        boolean exit = false;
        String command;

        System.out.println("--- Welcome to your account " + Name + " ---");
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
                    System.out.println("Booking functionality is not implemented yet.");
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
                    break;
                
                default:
                    System.out.println("Unknown command. For help, type -h.");
            }
        }
    }  
}