package eu.zoho.chaotx.uman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class offers methods to manage sets
 * of unique users.
 * 
 * @see User
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1;
    private HashMap<String, User> userMap;
    private File data_file;
    private User logged_in_user;

    /**
     * Constructs a new user manager.
     * The file at <i>data_file_path</i> will be used to
     * save and load users to and from disk.
     * 
     * @param data_file_path path to data file
     */
    public UserManager(String data_file_path) {
        userMap = new HashMap<>();
        data_file = new File(data_file_path);

        if(!data_file.exists())
            data_file.getParentFile().mkdirs();
    }

    /**
     * Adds a new user to the user map.
     * 
     * @param user new user
     */
    public void addUser(User user) {
        if(userMap.containsKey(user.getUid()))
            throw new IllegalArgumentException(
                "user '" + user.getUid() + "' already registered");

        userMap.put(user.getUid(), user);
    }

    /**
     * Returns the user map.
     * 
     * @return user map
     */
    public Map<String, User> getUserMap() {
        return Collections.unmodifiableMap(userMap);
    }

    /**
     * Sets passed user as logged in user.
     * 
     * @param user user to login
     * @param password password of the user
     * @throws IllegalArgumentException if user
     *  is not registered within the userMap or
     *  passed password is invalid
     */
    public void login(User user, String password) {
        if(!(user == userMap.get(user.getUid())))
            throw new IllegalArgumentException("User is not registered");

        if(!user.isValid(password))
            throw new IllegalArgumentException("Invalid password");

        logged_in_user = user;
    }

    /**
     * Logs out currently logged in user.
     */
    public void logout() {
        logged_in_user = null;
    }

    /**
     * Retrieves the currently logged in user.
     * 
     * @return logged in user or null if no user
     *  is logged in
     */
    public User getLoggedInUser() {
        return logged_in_user;
    }

    /**
     * Saves all users to disk.
     */
    public void save() {
        try(FileOutputStream fos = new FileOutputStream(data_file);
            ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(userMap);
        } catch(FileNotFoundException e1) {
            // TODO create file
            e1.printStackTrace();
        } catch(IOException e2) {
            // TODO handle error
            e2.printStackTrace();
        }
    }

    /**
     * Loads all users from disk.
     * The currently active user map will be
     * overwritten.
     */
    public void load() {
        try(FileInputStream fis = new FileInputStream(data_file);
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
            @SuppressWarnings("unchecked")
            HashMap<String, User> userMap = (HashMap<String, User>)ois.readObject();

            // https://stackoverflow.com/questions/509076/how-do-i-address-unchecked-cast-warnings
            for(String k : userMap.keySet());
            for(User v : userMap.values());
            this.userMap = userMap;
        } catch(FileNotFoundException e1) {
            // TODO create file
            e1.printStackTrace();
        } catch(IOException | ClassNotFoundException e2) {
            // TODO
            e2.printStackTrace();
        }
    }
}