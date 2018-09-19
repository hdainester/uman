package eu.zoho.chaotx.uman;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import eu.zoho.chaotx.crypt.AlphabetType;
import eu.zoho.chaotx.crypt.EncryptFunction;
import eu.zoho.chaotx.crypt.Encryptor;

/**
 * This class represents a user with password protected
 * access to its data.
 * 
 * @see UserManager
 */
public class User implements Serializable {
    public static final long serialVersionUID = 1;
    public static final int DEFAULT_TOKEN_LENGTH = 128;

    private int tlen;
    private String uid, token;
    private Map<String, String> dataMap;
    
    private Encryptor encryptor;
    private EncryptFunction encryptFunction;
    private EncryptFunction decryptFunction;

    /**
     * Constructs a new user object.
     * The passed <i>password</i> will not be saved.
     * Instead a random token is generated using the
     * password as seed.
     * 
     * @param name uid of the user
     * @param password of the user
     * @param token_length length of the token
     */
    public User(String name, String password, int token_length) {
        if(password.length() > token_length)
            throw new IllegalArgumentException(
                "password length may not be greater than token length");

        encryptor = new Encryptor();
        encryptor.setAlphabet(AlphabetType.ALPHA_NUMERIC);
        encryptFunction = (a, b) -> a+b;
        decryptFunction = (a, b) -> a-b;

        uid = name;
        tlen = token_length;
        token = encryptor.createToken(password, tlen);
        dataMap = new HashMap<>();
    }

    /**
     * Constructs a new user object.
     * The {@link #DEFAULT_TOKEN_LENGTH} will be used
     * as <i>token_length</i>.
     * 
     * @param name uid of the user
     * @param password of the user
     * @see #User(String, String, int)
     */
    public User(String name, String password) {
        this(name, password, DEFAULT_TOKEN_LENGTH);
    }

    /**
     * Returns the unique id of the user.
     * 
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Returns the token of this user.
     * 
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * Adds new {@code data} to this objects dataMap.
     * 
     * @param key of the data
     * @param data to add to Map
     * @param password to confirm validation
     * 
     * @throws IllegalArgumentException if password was invalid
     */
    public void setData(String key, String data, String password) {
        if(!isValid(password)) throw new IllegalArgumentException("invalid password");
        dataMap.put(key, encryptor.execute(data, password, encryptFunction));
    }

    /**
     * Decrypts and returns this objects data with the specified {@code key}.
     * 
     * @param key of the data
     * @param password to confirm validation
     * @return decrypted data
     * 
     * @throws IllegalArgumentException if password was invalid
     */
    public String getData(String key, String password) {
        if(!isValid(password)) throw new IllegalArgumentException("invalid password");
        return encryptor.execute(dataMap.get(key), password, decryptFunction);
    }

    /**
     * Checks wether the given password is valid for this user.
     * 
     * @param password to confirm validation
     * @return true if password is valid false otherwise
     */
    public boolean isValid(String password) {
        return encryptor.createToken(password, tlen).equals(token);
    }

    @Override
    public String toString() {
        return getUid();
    }
}