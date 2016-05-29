package net.carpoolme.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.zip.ZipOutputStream;

/**
 * Created by John Andersen on 5/28/16.
 */
public class ZipStorage implements Storage {
    public static final Charset CHARSET = Charset.forName("UTF-8");

    private String mZipPath = null;
    private boolean mFlush = false;
    private FileOutputStream zipFileOutput = null;

    public ZipStorage(String zipPath, boolean flush) throws FileNotFoundException {
        mZipPath = zipPath;
        mFlush = flush;
        FileOutputStream fileOutputStream = new FileOutputStream(mZipPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream, CHARSET);
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    public boolean enableWrite() {
        return false;
    }

    @Override
    public boolean disableWrite() {
        return false;
    }

    @Override
    public boolean writeRecord(Path recordPath, InputStream in) {
        return false;
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
        return null;
    }
}
