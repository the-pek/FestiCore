package org.esiea.festicore;
import org.esiea.festicore.service.BookingService;
import org.esiea.festicore.service.userDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Scanner sc = new Scanner(System.in);
        userDataService storageData = new userDataService();
        BookingService bookingService = new BookingService();

        List<User> users;
        try {
            users = storageData.loadUsers();
        } catch (IOException e) {
            users = new ArrayList<>();
            System.out.println("Users loaded: 0 (file not found or unreadable)");
        }


        int choix;
        User currentUser = null;
        Festival festival = new Festival();
        festival.getReservations().put(
                "T1",
                new Tickets("T1", 80f, java.time.LocalDate.parse("2026-07-15"), 10,
                        org.esiea.festicore.Enumeration.TicketType.day)
        );


        do {
            System.out.println(" ");
            System.out.println("============================================================");
            System.out.println("                     WELCOME TO FESTICORE                   ");
            System.out.println("============================================================");
            System.out.println(" ");
            System.out.println("Select an action to perform :");
            System.out.println("1. Login");
            System.out.println("2. Signup");
            System.out.println("3. Quit");
            System.out.println(" ");
            System.out.println("============================================================");
            System.out.println(" ");

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
                        System.out.println(" ");
                    } else {
                        System.out.println(" ");
                        System.out.println("Login successful");
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

                    } catch (IllegalArgumentException | IOException e) {
                        System.err.println("Error : "+e.getMessage());
                    }
                    break;
                case  3:
                    System.out.println("Bye!");
                    break;

                default:
                    System.err.println("Wrong choice. Enter a valid choice.");
                    System.out.println(" ");
            }
        } while (choix != 3 );
    }
}
