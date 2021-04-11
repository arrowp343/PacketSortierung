package roles;

import roles.idCard.IDCard;

public abstract class Employee {
    int id;
    String name;
    IDCard idCard;

    public Employee(String name, String role, String pin, String superPin) {
        idCard = new IDCard(name, role, pin, superPin);
        this.name = name;
    }

    public IDCard getIdCard() {
        return idCard;
    }
}