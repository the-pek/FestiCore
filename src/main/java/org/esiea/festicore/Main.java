package org.esiea.festicore;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    
    public void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("-h : Display help");
        System.out.println("-p : Show festival program");
        System.out.println("-r : Find reservation by ID");
        System.out.println("-b : Buy ticket, pass, or activity");
        System.out.println("-q : Quit the application");
    }

    static void main() {
        Scanner sc = new Scanner(System.in);
        User user = new User();
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

            switch (choix) {
                case 1:
                    System.out.println("Please enter your mail :");
                    String mail= sc.nextLine();
                    System.out.println("Please enter your password :");
                    String pass= sc.nextLine();

                    user.login(mail,pass);

                    break;
                case  2:
                    try {
                        System.out.println("Enter your name :");
                        String name= sc.nextLine();
                        System.out.println("Enter your email :");
                        String email= sc.nextLine();
                        System.out.println("Enter your phone number :");
                        String phone= sc.nextLine();
                        System.out.println("Enter a password : ");
                        String password= sc.nextLine();
                        System.out.println(" ");

                        User.register(name,email,phone,password);
                    } catch (IllegalArgumentException e) {
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
