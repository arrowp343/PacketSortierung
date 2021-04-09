package roles.idCard;

public class IDCardStatus_Invalid implements IIDCardStatus {
    public boolean readCard(IDCard idCard, String code){
        return false;
    }
}
