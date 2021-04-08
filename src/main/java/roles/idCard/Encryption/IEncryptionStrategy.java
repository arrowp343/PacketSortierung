package roles.idCard.Encryption;

public interface IEncryptionStrategy {
    String encrypt(String plain);
    String decrypt(String cipher);
}