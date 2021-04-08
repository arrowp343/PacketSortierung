package roles.idCard.Encryption;

public class MagnetStripe {
    private String cipher;
    public MagnetStripe(int id, String name, String role, String pin, String superPin){
        String plain = id + ";" + name + ";" + role + ";" + pin + ";" + superPin;
        //TODO encrypt with Strategy selected
    }
    public boolean checkPin(String pin){
        return false; //TODO
    }
    public boolean checkSuperPin(String superPin){
        return false; //TODO
    }
}