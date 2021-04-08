package roles.idCard;

public class IDCard {
    String cipher;
    IIDCardStatus status = new IDCardStatus_Active();

    public boolean checkPin(String pin){
        return false; //TODO
    }
    public boolean checkSuperPin(String superPin){
        return false; //TODO
    }
    public void setStatus(IIDCardStatus status){
        this.status = status;
    }
}
