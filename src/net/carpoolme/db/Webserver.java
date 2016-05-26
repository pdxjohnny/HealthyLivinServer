package net.carpoolme.db;

/**
 * Created by John Andersen on 5/13/16.
 */

import com.sun.net.httpserver.HttpServer;

import net.carpoolme.web.JWTProtectedWebserver;

public class Webserver extends JWTProtectedWebserver {
    private Database webDatabase = new Database();

    public Webserver() {
        setTokenSecret("secret");
        Object[][] testUser = new Object[][] {
            new Object[] {"id", 1},
            new Object[] {"username", "testuser"},
        };
        webDatabase.insert(Database.INTERNAL_USERS, testUser);
    }

    @Override
    protected void bindHandlers(HttpServer server) {
        super.bindHandlers(server);
    }

    protected User createUser() {
        return new User(getTokenAuth(), webDatabase);
    }
}
