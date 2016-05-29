package net.carpoolme.storage;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Created by John Andersen on 5/29/16.
 * MockStorage is fake storage that stores nothing put pretends like it is storing everything
 */
public class MockStorage implements Storage {
    @Override
    public boolean start() {
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

    @Override
    public boolean destroy() {
        return true;
    }

    @Override
    public boolean enableWrite() {
        return true;
    }

    @Override
    public boolean disableWrite() {
        return true;
    }

    @Override
    public boolean writeRecord(Path recordPath, InputStream in) {
        return true;
    }

    @Override
    public InputStream readRecord(Path recordPath) {
        return null;
    }

    @Override
    public InputStream[] allRecords() {
        return new InputStream[0];
    }

    @Override
    public Storage downLevel(Path dir) {
        return this;
    }
}
