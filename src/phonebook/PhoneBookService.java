package phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneBookService {

    private static final String DATA_PATH = "src/phonebook/contacts.csv";


    public static void loadContacts(List<User> users) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH))) {

            Pattern pattern = Pattern.compile("^([^,\\\"]{2,50}),([^,\\\"]{2,50}),\"(([^\"]{2,1000});([^\"]{2,1000}))+\"$");

            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }

                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    User user = new User();
                    user.setFirstName(matcher.group(1));
                    user.setLastName(matcher.group(2));
                    String[] contactsStringList = matcher.group(3).split(";\\s*");
                    List<Contact> contacts = new ArrayList<>();
                    for (String contactString : contactsStringList) {
                        String[] contactArray = contactString.split(",\\s*");
                        Contact contact = new Contact();
                        contact.setPhoneNumber(contactArray[0]);
                        contact.setPhoneNumberType(PhoneNumberType.valueOf(contactArray[1].toUpperCase()));
                        contact.setEmailType(EmailType.valueOf(contactArray[2].toUpperCase()));
                        contact.setEmail(contactArray[3]);
                        contact.setCompany(contactArray[4]);
                        contacts.add(contact);
                    }
                    user.setContacts(contacts);
                    users.add(user);
                }
            }


        } catch (IOException ioex) {
            System.err.println("Could not load contacts, phone book is empty!");
        }
    }


    public static void saveContacts(List<User> users) {
        try (PrintWriter writer = new PrintWriter(DATA_PATH)) {
            if (!users.isEmpty()) {
                for (User user : users) {
                    String line = Converter.convertUserToStringLine(user);
                    writer.println(line);
                }
            }

        } catch (IOException ioex) {
            System.err.println(ioex.getMessage());
        }
    }

    public static void listUsers(List<User> users) {
        if (!users.isEmpty()) {
            for (User user : users) {
                System.out.println(user.toString());
                System.out.println();
            }
        } else {
            System.out.println("No records found, the phone book is empty!");
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void showUser(List<User> users, Scanner input) {
        System.out.println("Enter the FirstName you are looking for:");
        String firstName = input.nextLine().trim();
        System.out.println("Enter the LastName you are looking for:");
        String lastName = input.nextLine().trim();

        User user = new User(firstName, lastName);
        if (users.contains(user)) {
            System.out.println(Converter.convertUserToStringLine(users.get(users.indexOf(user))));

        } else {
            System.out.println("Sorry, nothing found!");
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void findUser(List<User> users, Scanner input) {
        System.out.println("Enter a number to see to whom does it belong:");
        String number = input.nextLine().trim();

        while (!number.matches("^\\+?[0-9 ]{3,25}$")) {
            System.out.println("Invalid number! May contain only digits, spaces and '+'. Min length 3, max length 25.");
            System.out.println("Enter number:");
            number = input.nextLine().trim();
        }
        User foundUser = null;
        for (User user : users) {
            for (Contact contact : user.contacts) {

                if (number.equals(contact.getPhoneNumber())) {
                    foundUser = user;
                }
            }
        }
        if (foundUser != null) {
            System.out.println(foundUser);
        } else {
            System.out.println("Sorry, nothing found!");
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void addUser(List<User> users, Scanner input) {
        System.out.println("You are about to add a new contact to the phone book.");
        String firstName;
        String lastName;
        String phoneNumber;
        PhoneNumberType phoneNumberType;
        EmailType emailType;
        String email;
        String company;

        while (true) {
            System.out.println("Enter contact firstName:");
            firstName = input.nextLine().trim();
            if (firstName.matches("^.{2,50}$")) {
                break;
            } else {
                System.out.println("FirstName must be in range 2 - 50 symbols.");
            }
        }
        while (true) {
            System.out.println("Enter PhoneNumberType must be one of this values - Mobile, Work, Home or School:");
            try {
                phoneNumberType = PhoneNumberType.valueOf(input.nextLine().trim().toUpperCase());
                break;
            } catch (Exception ex) {
                System.out.println("PhoneNumberType must be one of this values - Mobile, Work, Home or School.");
            }
        }
        while (true) {
            System.out.println("Enter emailType must be one of this values - Gmail, Icloud or Other:");
            try {
                emailType = EmailType.valueOf(input.nextLine().trim().toUpperCase());
                break;
            } catch (Exception ex) {
                System.out.println("EmailType must be one of this values - Gmail, Icloud or Other.");
            }
        }

        while (true) {
            System.out.println("Enter contact lastName:");
            lastName = input.nextLine().trim();
            if (lastName.matches("^.{2,50}$")) {
                break;
            } else {
                System.out.println("LastName must be in range 2 - 50 symbols.");
            }
        }

        while (true) {
            System.out.println("Enter contact number:");
            phoneNumber = input.nextLine().trim();
            if (phoneNumber.matches("^\\+?[0-9 ]{3,25}$")) {
                break;
            } else {
                System.out.println("PhoneNumber may contain only '+', spaces and digits. Min length 3, max length 25.");
            }
        }

        System.out.println("Enter contact email:");
        email = input.nextLine().trim();

        System.out.println("Enter contact company:");
        company = input.nextLine().trim();

        User user = new User(firstName, lastName);
        if (users.contains(user)) {
            System.out.printf("'%s %s' already exists in the phone book!\n", firstName, lastName);


            for (User u : users) {
                boolean numberFound = false;
                for (Contact contact : u.contacts) {

                    if (phoneNumber.equals(contact.getPhoneNumber())) {
                        System.out.printf("Number %s already available for contact '%s %s'.\n", phoneNumber, u.getFirstName(), u.getLastName());
                        numberFound = true;
                    }
                }
                if (numberFound) {
                    Contact contact = new Contact(phoneNumber, phoneNumberType, emailType, email, company);
                    u.contacts.add(contact);
                    saveContacts(users);
                }
            }
        } else {
            Contact contact = new Contact(phoneNumber, phoneNumberType, emailType, email, company);
            User newUser = new User(firstName, lastName, Arrays.asList(contact));
            users.add(newUser);
            saveContacts(users);
            System.out.printf("Successfully added contact '%s %s' !\n", firstName, lastName);
        }

        System.out.println();
        System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
    }

    public static void editUser(List<User> users, Scanner input) {
        System.out.println("Enter the FirstName you would like to modify:");
        String firstName = input.nextLine().trim();
        System.out.println("Enter the LastName you would like to modify:");
        String lastName = input.nextLine().trim();

        User user = new User(firstName, lastName);
        if (users.contains(user)) {
            User foundUser = users.get(users.indexOf(user));
            System.out.printf("Current number(s) for %s %s:\n", firstName, lastName);
            for (Contact contact : foundUser.contacts) {
                System.out.println(contact.getPhoneNumber());
            }
            System.out.println();
            System.out.println("Would you like to add a new number or delete an existing number for this contact? [add/delete/cancel]");
            String editOption = input.nextLine().trim().toLowerCase();
            boolean addNumber = false;
            boolean delNumber = false;
            boolean chosen = false;

            while (!chosen) {
                switch (editOption) {
                    case "add":
                        addNumber = true;
                        chosen = true;
                        break;
                    case "delete":
                        delNumber = true;
                        chosen = true;
                        break;
                    case "cancel":
                        System.out.println("Contact was not modified!");
                        chosen = true;
                        break;
                    default:
                        System.out.println("Use 'add' to save a new number, 'delete' to remove an existing number or 'cancel' to go back.");
                        editOption = input.nextLine().trim().toLowerCase();
                        break;
                }
                if (addNumber) {
                    String phoneNumber;
                    PhoneNumberType phoneNumberType;
                    EmailType emailType;
                    String email;
                    String company;
                    while (true) {
                        System.out.println("Enter PhoneNumberType must be one of this values - Mobile, Work, Home or School:");
                        try {
                            phoneNumberType = PhoneNumberType.valueOf(input.nextLine().trim().toUpperCase());
                            break;
                        } catch (Exception ex) {
                            System.out.println("PhoneNumberType must be one of this values - Mobile, Work, Home or School.");
                        }
                    }
                    while (true) {
                        System.out.println("Enter emailType must be one of this values - Gmail, Icloud or Other:");
                        try {
                            emailType = EmailType.valueOf(input.nextLine().trim().toUpperCase());
                            break;
                        } catch (Exception ex) {
                            System.out.println("EmailType must be one of this values - Gmail, Icloud or Other.");
                        }
                    }

                    while (true) {
                        System.out.println("Enter contact number:");
                        phoneNumber = input.nextLine().trim();
                        if (phoneNumber.matches("^\\+?[0-9 ]{3,25}$")) {
                            break;
                        } else {
                            System.out.println("PhoneNumber may contain only '+', spaces and digits. Min length 3, max length 25.");
                        }
                    }

                    System.out.println("Enter contact email:");
                    email = input.nextLine().trim();

                    System.out.println("Enter contact company:");
                    company = input.nextLine().trim();
                    Contact contact = new Contact(phoneNumber, phoneNumberType, emailType, email, company);
                    foundUser.contacts.add(contact);
                    saveContacts(users);
                }
                if (delNumber) {
                    while (true) {
                        System.out.println("Enter the number you want to delete:");
                        String phoneNumber = input.nextLine().trim();
                        Contact foundContact = null;
                        for (Contact contact : foundUser.contacts) {

                            if (phoneNumber.equals(contact.getPhoneNumber())) {
                                System.out.printf("Number %s already available for contact '%s %s'.\n", phoneNumber, foundUser.getFirstName(), foundUser.getLastName());
                                foundContact = contact;
                            }
                        }
                        if (foundContact != null) {
                            foundUser.contacts.remove(foundContact);
                            saveContacts(users);
                            System.out.printf("Number was successfully deleted. Current number(s) for %s %s:\n", firstName, lastName);
                            for (Contact contact : foundUser.contacts) {
                                System.out.println(contact.getPhoneNumber());
                            }
                            break;
                        } else {
                            System.out.printf("Current number(s) for %s %s:\n", firstName, lastName);
                            for (Contact contact : foundUser.contacts) {
                                System.out.println(contact.getPhoneNumber());
                            }
                        }
                    }
                }
            }
            } else{
                System.out.println("Sorry, name not found!");
            }

            System.out.println();
            System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
        }


        public static void deleteUser (List < User > users, Scanner input){
            System.out.println("Enter the FirstName of the contact to be deleted:");
            String firstName = input.nextLine().trim();
            System.out.println("Enter the LastName of the contact to be deleted:");
            String lastName = input.nextLine().trim();

            User user = new User(firstName, lastName);
            if (users.contains(user)) {
                User foundUser = users.get(users.indexOf(user));
                System.out.printf("Contact '%s %s' will be deleted. Are you sure? [Y/N]:\n", firstName, lastName);
                String confirmation = input.nextLine().trim().toLowerCase();
                confirm:
                while (true) {
                    switch (confirmation) {
                        case "y":
                            users.remove(foundUser);
                            saveContacts(users);
                            System.out.println("Contact was deleted successfully!");
                            break confirm;
                        case "n":
                            break confirm;
                        default:
                            System.out.println("Delete contact? [Y/N]:");
                            break;
                    }
                    confirmation = input.nextLine().trim().toLowerCase();
                }
            } else {
                System.out.println("Sorry, name not found!");
            }

            System.out.println();
            System.out.println("Type a command or 'exit' to quit. For a list of valid commands use 'help':");
        }


    }