package roles.idCard.Encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class DES implements IEncryptionStrategy{
    SecretKeySpec secretKey;
    byte[] key;

    public String encrypt(String plainMessage, String key){
        try {
            setKey(key);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainMessage.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public String decrypt(String encryptedMessage, String key){
        try {
            setKey(key);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void setKey(String inputKey) {
        MessageDigest sha;
        try {
            key = inputKey.getBytes(StandardCharsets.UTF_8);
        //TODO key funktioniert nicht richtig: wrong key size
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "DES");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}