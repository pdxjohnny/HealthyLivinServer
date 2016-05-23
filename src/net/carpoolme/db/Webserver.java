package net.carpoolme.db;

/**
 * Created by John Andersen on 5/13/16.
 */

import com.sun.net.httpserver.HttpServer;

import net.carpoolme.web.JWTProtectedWebserver;

public class Webserver extends JWTProtectedWebserver {

    @Override
    protected void bindHandlers(HttpServer server) {
        super.bindHandlers(server);
    }

    protected User createUser() {
        return new User(getTokenAuth());
    }
}
