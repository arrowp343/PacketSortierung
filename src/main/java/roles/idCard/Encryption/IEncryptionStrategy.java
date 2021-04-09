package roles.idCard.Encryption;

public interface IEncryptionStrategy {
    String encrypt(String plainMessage, String key);
    String decrypt(String encryptedMessage, String key);
}