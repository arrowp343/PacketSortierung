package roles.idCard;

public class IDCardStatus_Locked implements IIDCardStatus{
    int wrongTries = 0;
    public boolean readCard(IDCard idCard, String code){
        if(!idCard.checkSuperPin(code)) {
            wrongTries++;
            if (wrongTries >= 2)
                idCard.setStatus(new IDCardStatus_Invalid());
            return false;
        } else {
            idCard.setStatus(new IDCardStatus_Active());
            return true;
        }
    }
}
