package application.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import Exceptions.WrongPasswordException;

public class CryptoHelper
{
    public static final int IV_LENGTH = 16; // bytes
    public static final int KEY_LENGTH = 256; // bits
    public static final int BLOCK_SIZE = 128; // bits
    public static final int HASH_ITERATIONS = 1500;

    private static final SecureRandom _random = new SecureRandom();

    private final BlockCipher engine = new RijndaelEngine(BLOCK_SIZE);
    private final BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(engine), new PKCS7Padding());
    private byte[] _hash;

    public CryptoHelper(char[] password, byte[] salt, byte[] sessionSalt)
    {
        String passwordHash = Base64.getEncoder().encodeToString(generateHash(password, salt));
        _hash = generateHash(passwordHash.toCharArray(), sessionSalt);
    }

    public byte[] Decrypt(byte[] buffer, byte[] iv) throws IllegalArgumentException
    {
        if (buffer.length % cipher.getBlockSize() != 0)
        {
            throw new IllegalArgumentException("Invalid block size.");
        }

        try
        {
            KeyParameter kp = new KeyParameter(_hash, 0, KEY_LENGTH / 8);
            CipherParameters cp = new ParametersWithIV(kp, iv, 0, IV_LENGTH);
            cipher.reset();
            cipher.init(false, cp);

            byte[] decrypted = new byte[cipher.getOutputSize(buffer.length)];
            int outputLen = cipher.processBytes(buffer, 0, buffer.length, decrypted, 0);
            outputLen += cipher.doFinal(decrypted, outputLen);

            byte[] output = new byte[outputLen];
            System.arraycopy(decrypted, 0, output, 0, outputLen);
            return output;
        }
        catch (DataLengthException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }

        catch (InvalidCipherTextException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static byte[] generateSalt()
    {
        byte[] salt = new byte[KEY_LENGTH / 8];
        _random.nextBytes(salt);
        return salt;
    }

    public static byte[] generateHash(char[] password, byte[] salt)
    {
        PBEKeySpec spec = new PBEKeySpec(password, salt, HASH_ITERATIONS, KEY_LENGTH);
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
}
