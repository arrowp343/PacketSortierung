package roles.idCard.Encryption;

import config.Configuration;
import config.EncryptionStrategy;

public class MagnetStripe {
    private final String cipher;
    private IEncryptionStrategy strategy;
    public MagnetStripe(int id, String name, String role, String pin, String superPin){
        String plain = id + ";" + name + ";" + role + ";" + pin + ";" + superPin;
        if(Configuration.encryptionStrategy == EncryptionStrategy.AES)
            strategy = new AES();
        else if (Configuration.encryptionStrategy == EncryptionStrategy.DES)
            strategy = new DES();
        cipher = strategy.encrypt(plain, Configuration.encryptionKey);
    }
    public boolean checkPin(String pin){
        return strategy.decrypt(cipher, Configuration.encryptionKey).split(";")[3].equals(pin);
    }
    public boolean checkSuperPin(String superPin){
        return strategy.decrypt(cipher, Configuration.encryptionKey).split(";")[4].equals(superPin);
    }
}