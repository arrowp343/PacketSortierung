package roles.idCard;

public class IDCardStatus_Active implements IIDCardStatus{
    int wrongTries = 0;
    public boolean readCard(IDCard idCard, String code){
        if(!idCard.checkPin(code)) {
            wrongTries++;
            if (wrongTries >= 3)
                idCard.setStatus(new IDCardStatus_Locked());
            return false;
        } else {
            wrongTries = 0;
            return true;
        }
    }
}
