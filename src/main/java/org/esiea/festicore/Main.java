package org.esiea.festicore;
import org.esiea.festicore.service.BookingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        JsonDataManager storageData = new JsonDataManager();
        BookingService bookingService = new BookingService();

        List<User> users;

        // 1. Initialize the logger at the very beginning
        LogManager.setup();
        
        // 2. Get the logger instance
        java.util.logging.Logger logger = LogManager.getLogger();
        
        // 3. Log the application start
        logger.info("FestiCore application is starting...");

        try {
            users = storageData.loadUsers();
        } catch (IOException e) {
            users = new ArrayList<>();
            System.out.println("Users loaded: 0 (file not found or unreadable)");
            logger.warning("Users loaded: 0 (file not found or unreadable)");
        }


        int choix;
        User currentUser = null;
        Festival festival = new Festival(); // ou chargé ailleurs


            System.out.println(" ");
            System.out.println("============================================================");
            System.out.println("                     WELCOME TO FESTICORE                   ");
            System.out.println("============================================================");
            System.out.println(" ");
        do {
            
            System.out.println("Select an action to perform :");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Quit");
            System.out.println(" ");
            System.out.println("============================================================");
            System.out.println(" ");

            logger.info("Waiting for user commands...");
            choix = Integer.parseInt(sc.nextLine());
            System.out.println("============================================================");

            switch (choix) {
                case 1:
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
                        currentUser.account(sc, festival, bookingService);
                    }
                    break;
                case  2:
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
                case  3:
                    System.out.println("Bye!");
                    logger.info("Application exiting by user request.");
                    break;

                default:
                    System.err.println("Wrong choice. Enter a valid choice.");
                    logger.warning("Wrong menu choice entered by user.");
                    System.out.println(" ");
            }
        } while (choix != 3 );
    }
}
