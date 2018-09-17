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
 * of users.
 * 
 * @see User
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1;
    private HashMap<String, User> userMap;
    private File data_file;

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
     * @param u new user
     */
    public void addUser(User u) {
        if(userMap.containsKey(u.getUid()))
            throw new IllegalArgumentException(
                "user '" + u.getUid() + "' already registered");

        userMap.put(u.getUid(), u);
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
     * Loads user all users from disk.
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