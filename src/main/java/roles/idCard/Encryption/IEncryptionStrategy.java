package roles.idCard.Encryption;

import javax.crypto.SecretKey;

public interface IEncryptionStrategy {
    String encrypt(String plain, SecretKey key);
    String decrypt(String cipher, SecretKey key);
}