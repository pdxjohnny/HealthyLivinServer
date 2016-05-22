package net.carpoolme.utils;

/**
 * Created by John Andersen on 5/22/16.
 */
public interface Parseable {
    Object[][] Marshal();
    boolean Unmarshal(Object [][] data);
}
