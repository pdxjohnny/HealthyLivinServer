package net.carpoolme.db;

/**
 * Created by John Andersen on 5/13/16.
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.carpoolme.storage.XML;
import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.Parser;
import net.carpoolme.web.JWTProtectedWebserver;

import java.io.IOException;
import java.io.OutputStream;

public class Webserver extends JWTProtectedWebserver {
    private Database webDatabase = new Database();
    private Parser parser = new JSONParser();

    public Webserver() {
        setTokenSecret("secret");
        Object[][] testUser = new Object[][] {
                new Object[] {"id", 1},
                new Object[] {"username", "testuser"},
                new Object[] {"password", "password"},
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

    private class addUser implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
//            if (requireLogin(t) == null) {
//                return;
//            }
            int status = 500;
            String response = "Something went wrong";
            Object[][] request = (Object[][]) new XML().toObject(t.getRequestBody());
            if (request == null) {
                response = "Could not create user, bad user info";
                status = 400;
            } else if (parser.getKey(request, "id") == null ||
                    parser.getKey(request, "username") == null ||
                    parser.getKey(request, "password") == null) {
                response = "Could not create user, need id, username, and password";
                status = 400;
            } else if (webDatabase.insert(Database.INTERNAL_USERS, request)) {
                response = "Successfully created user";
                status = 200;
            }
            t.sendResponseHeaders(status, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
