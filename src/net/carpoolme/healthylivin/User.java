package net.carpoolme.healthylivin;

/**
 * Created by John Andersen on 5/13/16.
 */

import java.io.InputStream;
import java.util.Scanner;

public class User extends Object {
    private JWT token;

    private int id = 0;
    private String username = "Not Logged In";
    private String password = "";

    User() {
        this(JWT.fromEnv());
    }

    User(JWT mToken) {
        token = mToken;
    }

    private boolean login() {
        return true;
    }

    public boolean fromStream(InputStream in) {
        return true;
    }

    @Override
    public String toString() {
        return Token();
    }

    public String Token() {
        if (!login()) {
            return JWT.JWT_FAILURE;
        }

        return token.toString(this);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
