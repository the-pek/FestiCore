package org.esiea.festicore;
import org.esiea.festicore.service.BookingService;
import org.esiea.festicore.service.JsonDataManager;
import org.esiea.festicore.service.LogManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
    static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        JsonDataManager storageData = new JsonDataManager();
        BookingService bookingService = new BookingService();
        Festival festival = new Festival();

        List<User> users;

        LogManager.setup();
        java.util.logging.Logger logger = LogManager.getLogger();
        logger.info("FestiCore application is starting...");

        try {
            users = storageData.loadUsers();
        } catch (IOException e) {
            users = new ArrayList<>();
            System.out.println("Users loaded: 0 (file not found or unreadable)");
            logger.warning("Users loaded: 0 (file not found or unreadable)");
        }

        Map<String, Reservation> stock;
        try {
            stock = storageData.loadReservations();
        } catch (IOException e) {
            stock = new java.util.HashMap<>();
        }
        if (stock.isEmpty()) {
            stock = bookingService.createDefaultReservations();
            storageData.saveReservations(stock);
            IO.println("Storage was not load");
        }
        festival.setReservations(stock);


        String choix;
        User currentUser;


            System.out.println(" ");
            System.out.println("============================================================");
            System.out.println("                     WELCOME TO FESTICORE                   ");
            System.out.println("============================================================");
            System.out.println(" ");

        do {
            System.out.println("Select an action to perform :");
            System.out.println("1.Login -c");
            System.out.println("2.Signup -i");
            System.out.println("3.Quit -q");
            System.out.println("4.Help -h");
            System.out.println(" ");
            System.out.println("============================================================");
            System.out.println(" ");

            logger.info("Waiting for user commands...");
            choix = sc.nextLine();
            System.out.println("============================================================");

            switch (choix) {
                case "-c":
                    System.out.println("Please enter your mail :");
                    String mail = sc.nextLine();
                    System.out.println("Please enter your password :");
                    String pass = sc.nextLine();

                    currentUser = null;

                    for (User u : users) {
                        if (u.login(mail, pass)) {
                            currentUser = u;
                            break;
                        }
                    }
                    if (currentUser == null) {
                        System.err.println("Invalid credentials. Please try again.");
                        logger.warning("Invalid credentials attempt for email: " + mail);
                        System.out.println(" ");
                    } else {
                        System.out.println(" ");
                        System.out.println("Login successful");
                        logger.info("User logged in: " + currentUser.getEmail());
                        System.out.println("============================================================");
                        currentUser.account(sc, festival, bookingService, storageData);
                    }
                    break;
                case  "-i":
                    try {
                        System.out.println(" ");
                        System.out.println("Enter your name :");
                        String name= sc.nextLine();
                        System.out.println("Enter your email :");
                        String email= sc.nextLine();
                        System.out.println("Enter your phone number :");
                        String phone= sc.nextLine();
                        System.out.println("Enter a password : ");
                        String password= sc.nextLine();
                        System.out.println(" ");

                        User newUser = User.register(name,email,phone,password);
                        users.add(newUser);
                        storageData.saveUsers(users);
                        logger.info("New user registered: " + newUser.getEmail());

                    } catch (IllegalArgumentException | IOException e) {
                        logger.severe("Critical application failure: " + e.getMessage());
                        System.err.println("Error : "+e.getMessage());
                    }
                    break;
                case  "-q":
                    System.out.println("Bye!");
                    logger.info("Application exiting by user request.");
                    return;

                case "-h":
                    System.out.println("Help Menu:");
                    System.out.println("-c : Login to your account");
                    System.out.println("-i : Create a new account");
                    System.out.println("-q : Quit the application");
                    System.out.println("-h : Display this help menu");
                    break;
                default:
                    System.err.println("Unknown command. For help, type -h.");
                    logger.warning("Wrong menu choice entered by user.");
                    System.out.println(" ");
            }
        } while (!choix.equals("-q"));
    }
}
