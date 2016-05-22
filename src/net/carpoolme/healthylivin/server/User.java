package net.carpoolme.healthylivin.server;

import net.carpoolme.utils.JWT;

/**
 * Created by John Andersen on 5/22/16.
 */
public class User extends net.carpoolme.healthylivin.User {
    private JWT token;

    User() {
        token = JWT.fromEnv();
    }

    public boolean login() {
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
}
