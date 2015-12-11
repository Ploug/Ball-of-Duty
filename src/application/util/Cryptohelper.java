package application.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptohelper
{
    public final static int IV_LENGTH = 16;
    public final static int KEY_LENGTH =  256;

    public static byte[] Decrypt(byte[] buffer, byte[] iv, char[] password, byte[] salt, byte[] sessionSalt)
    {

        Cipher cipher;
        byte[] ciphertext = null;
        try
        {
            PBEKeySpec spec = new PBEKeySpec(password, salt, 1500, KEY_LENGTH);
            Arrays.fill(password, Character.MIN_VALUE);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes =  skf.generateSecret(spec).getEncoded();
            char[] keyChars = new char[keyBytes.length];
            
            for(int i = 0; i<keyBytes.length;i++)
            {
             keyChars[i] = (char)(keyBytes[i] & 0xFF);
            }
            
            
            PBEKeySpec specSession = new PBEKeySpec("eL6zDhqnvXaiLN3qfcIXMpoN8Dn2FL3SypohWD17T6c=".toCharArray(), sessionSalt, 1500, KEY_LENGTH);
            byte[] encodedSomething = skf.generateSecret(specSession).getEncoded();
            SecretKeySpec key = new SecretKeySpec(encodedSomething, "AES");
            

            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            ciphertext = cipher.doFinal(buffer);

        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        catch (NoSuchPaddingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InvalidAlgorithmParameterException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (BadPaddingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (buffer.length % 16 != 0) return null;

        return ciphertext;
    }

}