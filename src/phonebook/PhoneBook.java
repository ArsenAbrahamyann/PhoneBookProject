package phonebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhoneBook {

    public static void main(String[] args) {

        System.out.println("My Phone Book Application");
        System.out.println("===========================");
        System.out.println("Type a command or 'exit' to quit:");
        listCommands();
        System.out.print("> ");

        List<User> users = new ArrayList<>();
        PhoneBookService.loadContacts(users);

        Scanner input = new Scanner(System.in);
        String line = input.nextLine().trim();

        while (!line.equals("exit")) {

            switch (line) {
                case "list":
                    PhoneBookService.listUsers(users);
                    break;
                case "show":
                    PhoneBookService.showUser(users, input);
                    break;
                case "find":
                    PhoneBookService.findUser(users, input);
                    break;
                case "add":
                    PhoneBookService.addUser(users, input);
                    break;
                case "edit":
                    PhoneBookService.editUser(users, input);
                    break;
                case "delete":
                    PhoneBookService.deleteUser(users, input);
                    break;
                case "help":
                    listCommands();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }


            System.out.print("\n> ");
            line = input.nextLine().trim();
        }

        System.out.println("'My Phone Book Application' terminated.");
    }

    private static void listCommands() {
        System.out.println("list - lists all saved contacts in alphabetical  order");
        System.out.println("show - finds a contact by name");
        System.out.println("find - searches for a contact by number");
        System.out.println("add - saves a new contact entry into the phone book");
        System.out.println("edit - modifies an existing contact");
        System.out.println("delete - removes a contact from the phone book");
        System.out.println("help - lists all valid commands");
        System.out.println("---------------------------");
    }


}
