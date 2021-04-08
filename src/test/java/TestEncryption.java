import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import roles.idCard.Encryption.DES;
import roles.idCard.Encryption.IEncryptionStrategy;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class TestEncryption {
    IEncryptionStrategy strategy;
    @Test
    public void testDES(){
        try {
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            strategy = new DES();
            String password = "dhbw";   //TODO dafür sorgen dass schlüssel mit dhbw generiert wird.

            String plain = "Hallo";
            String encrypted = strategy.encrypt(plain, key);
            String decrypted = strategy.decrypt(encrypted, key);

            System.out.println("Plain : " + plain);
            System.out.println("Encrypted : " + encrypted);
            System.out.println("Decrypted : " + decrypted);
            assertEquals(plain, decrypted);
            assertNotEquals(plain, encrypted);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
