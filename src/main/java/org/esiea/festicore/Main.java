package org.esiea.festicore;
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
        List<User> users;

        try {
            users = storageData.loadUsers();
        } catch (IOException e) {
            users = new ArrayList<>();
        }

        int choix;

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

            choix = sc.nextInt();
            System.out.println("============================================================");

            switch (choix) {
                case 1:
                    System.out.println("Please enter your mail :");
                    String mail= sc.next();
                    System.out.println("Please enter your password :");
                    String pass= sc.next();
                    boolean loggedIn = false;
                    for (User u : users) {
                        if (u.login(mail, pass)) {
                            System.out.println("Login successful. Welcome " + u.getName());
                            loggedIn = true;
                            break;
                        }
                    }
                    if (!loggedIn) {
                        System.err.println("Invalid credentials. Please try again.");
                    }

                    break;
                case  2:
                    try {
                        System.out.println(" ");
                        System.out.println("Enter your name :");
                        String name= sc.next();
                        System.out.println("Enter your email :");
                        String email= sc.next();
                        System.out.println("Enter your phone number :");
                        String phone= sc.next();
                        System.out.println("Enter a password : ");
                        String password= sc.next();
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
