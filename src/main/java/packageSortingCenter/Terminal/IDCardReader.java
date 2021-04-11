package packageSortingCenter.Terminal;

import roles.idCard.IDCard;

public class IDCardReader {
    public boolean read(IDCard idCard, String pin) {
        return idCard.read(pin);
    }
}
