package net.carpoolme.auth;

/**
 * Created by John Andersen on 5/22/16.
 */
public abstract class JWTUser extends User {
    private JWT token;

    public JWTUser() throws NoToken {
        this(null);
    }

    public JWTUser(JWT mToken) {
        token = mToken;
    }

    @Override
    public String toString() {
        return token != null ? token.toString(this) : null;
    }
}
