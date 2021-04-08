package roles.idCard.Encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DES implements IEncryptionStrategy{
    public String encrypt(String plain, SecretKey key){
        try {
            Cipher ecipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipher = plain.getBytes(StandardCharsets.UTF_8);
            byte[] enc = ecipher.doFinal(cipher);
            enc = Base64.getEncoder().encode(enc);
            return new String(enc);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public String decrypt(String cipher, SecretKey key){
        try {
            Cipher dcipher = Cipher.getInstance("DES");
            dcipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plain = Base64.getDecoder().decode(cipher);
            byte[] dec = dcipher.doFinal(plain);
            return new String(dec, StandardCharsets.UTF_8);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}