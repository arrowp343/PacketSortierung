package roles.idCard;

import roles.idCard.Encryption.MagnetStripe;

public class IDCard {
    private final MagnetStripe magnetStripe;
    private IIDCardStatus status;
    private static int autoIncId = 0;

    public IDCard(String name, String role, String pin, String superPin){
        status = new IDCardStatus_Active();
        magnetStripe = new MagnetStripe(autoIncId++, name, role, pin, superPin);
    }

    public boolean read(String pin){
        return status.readCard(this, pin);
    }
    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }
    public void setStatus(IIDCardStatus status){
        this.status = status;
    }
}
