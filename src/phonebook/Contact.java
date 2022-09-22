package phonebook;

public class Contact {
    String phoneNumber;
    PhoneNumberType phoneNumberType;
    EmailType emailType;
    String email;
    String company;

    public Contact() {
    }

    public Contact(String phoneNumber, PhoneNumberType phoneNumberType, EmailType emailType, String email, String company) {
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
        this.emailType = emailType;
        this.email = email;
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
