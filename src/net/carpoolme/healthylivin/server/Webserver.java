package net.carpoolme.healthylivin.server;

/**
 * Created by John Andersen on 5/13/16.
 */

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.carpoolme.utils.JSONParser;
import net.carpoolme.utils.JWT;
import net.carpoolme.utils.Parser;
import net.carpoolme.utils.Strings;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Webserver {
    public static final int DEFAULT_PORT = 8000;
    public static final String TOKEN_SECRET = Strings.random();

    private HttpServer server;
    private Parser parser = new JSONParser();
    private JWT tokenAuth = JWT.fromEnv(TOKEN_SECRET);

    public void start(int port) throws Exception {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/login/", new APILoginHandler());
        server.setExecutor(null);
        server.start();
    }

    private User createUser() {
        return new User(tokenAuth);
    }

    private class APILoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            User user = createUser();
            parser.parse(t.getRequestBody(), user);
            String response = user.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
