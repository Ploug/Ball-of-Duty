package application.account;

import application.util.CryptoHelper;

public class Account
{
    private byte[] _salt;
    private byte[] _hash;

    public Account(String userName, char[] password)
    {
        _salt = CryptoHelper.generateSalt();
        _hash = CryptoHelper.generateHash(password, _salt);
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
