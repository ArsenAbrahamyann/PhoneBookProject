package phonebook;

public class Converter {

    public static String convertUserToStringLine (User user) {
        StringBuilder contactsString = new StringBuilder();
        for (Contact contact: user.contacts) {
            String contactString = String.format("%s,%s,%s,%s,%s;",
                    contact.getPhoneNumber(),
                    contact.getPhoneNumberType().getName(),
                    contact.getEmailType().getName(),
                    contact.getEmail(),
                    contact.getCompany());
            contactsString.append(contactString);

        }

        return String.format("%s,%s,\"%s\"",
                user.getFirstName(),user.getLastName(), contactsString);
    }
}
