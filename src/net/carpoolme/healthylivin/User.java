package net.carpoolme.healthylivin;

import net.carpoolme.utils.Parseable;
import net.carpoolme.utils.Parser;

/**
 * Created by John Andersen on 5/13/16.
 */

public abstract class User extends Object implements Parseable {
    private int id = 0;
    private String username = "Not Logged In";
    private String password = "";

    public Object[][] Marshal() {
        Object[] id = new Object[] {"uid", getId()};
        Object[] username = new Object[] {"username", getUsername()};
        return new Object[][] {id, username};
    }

    public boolean Unmarshal(Object[][] data) {
        id = (int) Parser.getKey(data, "id", 0);
        username = (String) Parser.getKey(data, "username", "Not Logged In");
        password = (String) Parser.getKey(data, "password", "");
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
