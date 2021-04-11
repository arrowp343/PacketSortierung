package roles;

public class Administrator extends Employee {
    Profile profile;

    public Administrator(String name, String pin, String superPin) {
        super(name, "administrator", pin, superPin);
    }
}
