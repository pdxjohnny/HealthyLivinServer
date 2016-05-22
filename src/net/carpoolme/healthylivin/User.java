package net.carpoolme.healthylivin;

import net.carpoolme.utils.BasicParser;
import net.carpoolme.utils.Parseable;

/**
 * Created by John Andersen on 5/13/16.
 */

public abstract class User extends Object implements Parseable {
    private BasicParser parser = new BasicParser();

    private int id = 0;
    private String username = "Not Logged In";
    private String password = "No password";

    // Set to true if you want to the password to be included in a Marshal
    private boolean MARSHAL_PASSWORD = false;

    public Object[][] Marshal() {
        Object[] mId = new Object[] {"uid", getId()};
        Object[] mUsername = new Object[] {"username", getUsername()};
        if (MARSHAL_PASSWORD) {
            Object[] mPassword = new Object[]{"password", password};
            return new Object[][] {mId, mUsername, mPassword};
        }
        return new Object[][] {mId, mUsername};
    }

    public boolean Unmarshal(Object[][] data) {
        id = (int) parser.getKey(data, "id", 0);
        username = (String) parser.getKey(data, "username", "Not Logged In");
        password = (String) parser.getKey(data, "password", "");
        return true;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public abstract boolean login();
}
