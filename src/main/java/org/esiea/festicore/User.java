package org.esiea.festicore;
import java.util.*;
import java.time.LocalDate;

public class User {
    private String Id;
    private String Name;
    private String Email;
    private String Phone;
    private String Password;
    private LocalDate registrationDate;
    private List<Reservation> history;

    public User(String id, String name, String email, String phone, String password, LocalDate registrationDate, List<Reservation> history) {
        this.Id = id;
        this.Name = name;
        this.Email = email;
        this.Phone = phone;
        this.Password = password;
        this.registrationDate = registrationDate;
        this.history = history;
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

    public void account(Scanner scanner, Festival festival) {
        boolean exit = false;
        String command;

        System.out.println("---Welcome to your account " + Name + "---");
        while (!exit) {
            command = scanner.nextLine().trim();
            switch (command) {
                case "-a":
                    if (history == null || history.isEmpty()) {
                        System.out.println("No reservation history available.");
                    } else {
                        for (Reservation reservation : history) {
                            System.out.println(reservation);
                        }
                    }
                    break;

                case "-r":
                    System.out.println("Enter reservation ID to find:");
                    String reservationId = scanner.nextLine().trim();
                    
                case "-h":
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
    } 
}