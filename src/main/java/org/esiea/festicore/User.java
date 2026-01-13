package org.esiea.festicore;
import java.util.List;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public static String generateUniqueId() {
        return "USER-" + java.util.UUID.randomUUID().toString();
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhone(String phone) {
        String phoneRegex = "^(\\+33|0)[1-9](\\d{2}){4}$";
        return phone.matches(phoneRegex);
    }

    public static User register(String name, String email, String phone, String password) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid Email.");
        }
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        User newUser = new User(generateUniqueId(), name, email, phone, password, LocalDate.now(), null);
        System.out.println(newUser.getName() + " has been created successfully");

        return newUser;
    }
}