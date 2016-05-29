package net.carpoolme.storage;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Created by John Andersen on 5/28/16.
 * Storage can be anything. It just needs to provide ways to read and write records
 */
public interface Storage {
    boolean start();
    boolean stop();
    boolean destroy();
    boolean enableWrite();
    boolean disableWrite();
    boolean writeRecord(Path recordPath, InputStream in);
    InputStream readRecord(Path recordPath);
    InputStream[] allRecords();
    Storage downLevel(Path dir);
}
