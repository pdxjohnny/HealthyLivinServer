/**
 * Created by John Andersen on 5/13/16.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Webserver {
    private HttpServer server;

    public void start() throws Exception {
        server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/odd", new OddNumbers());
        server.createContext("/odd/10", new OddNumbersTenPerLine());
        server.createContext("/odd/35", new OddNumbers35());
        server.createContext("/input", new Scanner());
        server.createContext("/add/employee", new EmployeeHandler());
        server.createContext("/api/login/", new APILoginHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static class OddNumbers implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response for URI: ";
            response += t.getRequestURI() + "\n";
            for (int i = 1; i < 101; i++) {
                if (i % 2 != 0){
                    response += i + "\n";
                }
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class OddNumbersTenPerLine implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response for URI: ";
            response += t.getRequestURI() + "\n";
            for (int i = 1; i < 101; i++) {
                if (i % 2 != 0){
                    response += String.format("%d ", i);
                }
                if (i % 20 == 0) {
                    response += "\n";
                }
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class OddNumbers35 implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            int lineBreak = 0;
            String response = "This is the response for URI: ";
            response += t.getRequestURI() + "\n";
            for (int i = 1; i < 101; i++) {
                if (i % 2 != 0 && i % 3 != 0 && i % 5 != 0) {
                    response += String.format("%d ", i);
                    ++lineBreak;
                }
                if (lineBreak >= 9) {
                    response += "\n";
                    lineBreak = 0;
                }
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class Scanner implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response for URI: ";
            response += t.getRequestURI() + "\n";
            java.util.Scanner input = new java.util.Scanner(t.getRequestBody());
            for (int i = 0; input.hasNext(); i++) {
                response += String.format("%d: %s\n", i, input.next());
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class EmployeeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            Employee e = new Employee();
            e.fromStream(t.getRequestBody());
            response += e;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private static class APILoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            User user = new User();
            user.fromStream(t.getRequestBody());
            String response = user.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
