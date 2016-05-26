package net.carpoolme.web;

/**
 * Created by John Andersen on 5/13/16.
 */

import net.carpoolme.auth.JWT;
import net.carpoolme.auth.User;
import net.carpoolme.utils.Parser;
import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.Strings;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.OutputStream;

public abstract class JWTProtectedWebserver extends BasicWebserver {
    public static final String TOKEN_SECRET = Strings.random();

    public static String LOGIN_FAILED = "{\"error\": \"Unknown login error\"}";
    public static String LOGIN_ERROR = "{\"error\": \"%s\"}";

    private Parser parser = new JSONParser();
    private JWT tokenAuth = JWT.fromEnv(TOKEN_SECRET);

    @Override
    protected void bindHandlers(HttpServer server) {
        server.createContext("/api/login/", new APILoginHandler());
    }

    protected abstract User createUser();

    private class APILoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            User user = createUser();
            parser.parse(t.getRequestBody(), user);
            // Try to log the user in
            String response = LOGIN_FAILED;
            try {
                if (user.login()) {
                    response = user.toString();
                }
            } catch (LoginException err) {
                response = String.format(LOGIN_ERROR, err.getMessage());
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public JWT getTokenAuth() {
        return tokenAuth;
    }

    protected JWT setTokenSecret(String tokenSecret) {
        tokenAuth = new JWT(tokenSecret);
        return getTokenAuth();
    }
}
