package phonebook;

public enum EmailType {
    GMAIL("Gmail"),
    ICLOUD("Icloud"),
    OTHER("Other");
    private String name;

    EmailType(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
