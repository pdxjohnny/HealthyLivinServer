package net.carpoolme.db;

import net.carpoolme.auth.JWT;
import net.carpoolme.auth.JWTUser;

import javax.security.auth.login.LoginException;

/**
 * Created by John Andersen on 5/22/16.
 */
public class User extends JWTUser {
    public User(JWT mToken) {
        super(mToken);
    }

    public boolean login() throws LoginException {
        throw new LoginException("Invalid username or password");
    }
}
