package net.carpoolme.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by John Andersen on 6/3/16.
 */
public class Prompt extends PrintStream {
    private InputStream in;

    public Prompt(InputStream mIn, OutputStream mOut) {
        super(mOut);
        in = mIn;
    }

    public boolean confirm() {
        return confirm(true);
    }

    public boolean confirm(boolean noPrecedence) {
        if (noPrecedence) {
            print("[y/N] ");
        } else {
            print("[Y/n] ");
        }
        byte[] check = new byte[1];
        try {
            boolean result = in.read(check) == 1 &&
                    Character.toUpperCase(check[0]) == 'Y';
            flushInput();
            return result;
        } catch (IOException ignored) {}
        return false;
    }

    private boolean flushInput() {
        try {
            byte[] array = new byte[1024];
            while (in.available() > 0) {
                in.read(array);
            }
        } catch (IOException ignored) {}
        return true;
    }
}
