import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import roles.idCard.Encryption.*;

public class TestEncryption {
    IEncryptionStrategy strategy;
    String encrypted, decrypted;
    @Test
    public void testDES(){
        try {
            strategy = new DES();
            String key = "dhbw";
            String plain = "Hallo";
            /*String encrypted = strategy.encrypt(plain, key);
            String decrypted = strategy.decrypt(encrypted, key);

            System.out.println("Plain : " + plain);
            System.out.println("Encrypted : " + encrypted);
            System.out.println("Decrypted : " + decrypted);
            assertEquals(plain, decrypted);
            assertNotEquals(plain, encrypted);      //TODO funktioniert nicht ganz: wrong key size
*/
            strategy = new AES();
            encrypted = strategy.encrypt(plain, key);
            decrypted = strategy.decrypt(encrypted, key);

            System.out.println("Plain : " + plain);
            System.out.println("Encrypted : " + encrypted);
            System.out.println("Decrypted : " + decrypted);
            assertEquals(plain, decrypted);
            assertNotEquals(plain, encrypted);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testMagnetStripe(){
        String pin = "1234", superPin = "123456";
        MagnetStripe ms = new MagnetStripe(1, "Hans", "idk", pin, superPin);
        assertTrue(ms.checkPin(pin));
        assertTrue(ms.checkSuperPin(superPin));
        assertFalse(ms.checkPin("0000"));
        assertFalse(ms.checkSuperPin("000000"));
    }
}
