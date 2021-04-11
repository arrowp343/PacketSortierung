package roles;

public class SuperVisor extends Employee {
    boolean isSenior;

    public SuperVisor(String name, String pin, String superPin) {
        super(name, "supervisor", pin, superPin);
    }
}
