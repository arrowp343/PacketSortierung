package roles.idCard;

import roles.idCard.Encryption.MagnetStripe;

public class IDCard {
    private MagnetStripe magnetStripe;
    private IIDCardStatus status;

    public IDCard(){
        status = new IDCardStatus_Active();
        // TODO new magnetstripe
    }

    public MagnetStripe getMagnetStripe() {
        return magnetStripe;
    }
    public void setStatus(IIDCardStatus status){
        this.status = status;
    }
}
