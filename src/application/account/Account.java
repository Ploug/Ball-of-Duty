package application.account;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Account
{
    private byte[] _salt;
    private byte[] _hash;
    SecureRandom _random = new SecureRandom();

    public Account(String userName, char[] password)
    {
        _salt = generateSalt();
        _hash = generateHash(password, _salt);
    }

    public byte[] generateSalt()
    {
        byte[] salt = new byte[32];
        _random.nextBytes(salt);
        return salt;
    }

    public byte[] generateHash(char[] password, byte[] salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, 1500, 256);
        Arrays.fill(password, Character.MIN_VALUE);
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally
        {
            spec.clearPassword();
        }
    }

    public byte[] getSalt()
    {
        return _salt;
    }

    public byte[] getHash()
    {
        return _hash;
    }

}
