package net.carpoolme.db;

import net.carpoolme.auth.JWT;
import net.carpoolme.auth.JWTUser;

import javax.security.auth.login.LoginException;

/**
 * Created by John Andersen on 5/22/16.
 */
public class User extends JWTUser {
    private Database loginDB;

    public User(JWT mToken, Database mLoginDB) {
        super(mToken);
        loginDB = mLoginDB;
    }

    public boolean login() throws LoginException {
        System.out.println("DEBG: Password \"" + password + "\"");
        if (password == null || password.length() < 1) {
            throw new LoginException("You must provide your password");
        }
        Object[][] userData = loginDB.select(Database.INTERNAL_USERS, "username", username);
        Object storedPassword = parser.getKey(userData, "password");
        if (storedPassword == null) {
            throw new LoginException("Something went wrong, you have no password");
        }
        if (!storedPassword.equals(password)) {
            throw new LoginException("Invalid username or password");
        }
        return true;
    }
}
