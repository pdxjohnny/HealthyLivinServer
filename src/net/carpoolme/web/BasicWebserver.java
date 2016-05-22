package net.carpoolme.web;

/**
 * Created by John Andersen on 5/13/16.
 */

import com.sun.net.httpserver.HttpServer;
import net.carpoolme.utils.Strings;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class BasicWebserver {
    public static final int DEFAULT_PORT = 8000;
    // You bet your ass we want threads
    public static boolean THREADING = true;
    // Env vars
    public static String ENV_PORT = "PORT";

    private HttpServer httpServer;
    private int port = -1;

    protected abstract void bindHandlers(HttpServer server);

    public int portFromEnv() {
        return portFromEnv(DEFAULT_PORT);
    }

    public int portFromEnv(int ifNotInEnv) {
        // Determine which port to serve on
        String portString = null;
        try {
            portString = System.getenv(ENV_PORT);
        } catch (NullPointerException | SecurityException ignored) {}
        if (portString == null) {
            portString = String.format("%d", ifNotInEnv);
        }
        port = Strings.toInt(portString);
        return port;
    }

    public void start() throws Exception {
        // Make sure we have a port
        if (port == -1) {
            portFromEnv();
        }
        // In case they chose zero
        InetSocketAddress address = new InetSocketAddress(port);
        port = address.getPort();
        System.out.printf("INFO: serving on port %d%n", port);
        // Bind to the address and use the system default backlog for socket accepts
        httpServer = HttpServer.create(address, 0);

        // Bind the derived classes handlers
        bindHandlers(httpServer);

        // Threading makes it so se can serve multiple requests in parallel
        if (THREADING) {
            int cores = Runtime.getRuntime().availableProcessors();
            // We want to run at least two threads, or else there is no point in threading
            if (cores < 2) {
                cores = 2;
            }
            httpServer.setExecutor(new ScheduledThreadPoolExecutor(cores));
        } else {
            // Sad no threads :-(
            httpServer.setExecutor(null);
        }
        // Onward to glory
        httpServer.start();
    }

    public int getPort() {
        return port;
    }
}
