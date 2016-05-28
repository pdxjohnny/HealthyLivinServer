package net.carpoolme.storage;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystemException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by John Andersen on 5/28/16.
 */
public class FileSystemStorage implements Storage {
    private Path baseDir = Paths.get("storage");
    private File baseDirHandle = null;
    private boolean writeEnabled = false;

    public FileSystemStorage() throws FileSystemException {
        createBaseDir();
    }

    public FileSystemStorage(Path mBaseDir) throws FileSystemException {
        baseDir = mBaseDir;
        createBaseDir();
    }

    // Makes sure we can read and write from the baseDir directory
    private void createBaseDir() throws FileSystemException {
        baseDirHandle = baseDir.toFile();
        if (!baseDirHandle.exists()) {
            if (!baseDirHandle.mkdirs()) throw new FileSystemException(baseDir.toAbsolutePath().toString());
        } else if (!baseDirHandle.isDirectory()) {
            throw new DirectoryNotEmptyException(baseDir.toAbsolutePath().toString());
        }
        enableWrite();
    }

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
        return destroyRecursive(baseDir.toFile());
    }

    public static boolean destroyRecursive(File remove) {
        if (remove == null) {
            return true;
        }
        if (remove.isDirectory()) {
            File[] files = remove.listFiles();
            for (File file : files) {
                if (!destroyRecursive(file)) {
                    return false;
                }
            }
        }
        return remove.delete();
    }

    @Override
    public boolean enableWrite() {
        writeEnabled = true;
        return true;
    }

    @Override
    public boolean disableWrite() {
        writeEnabled = false;
        return false;
    }

    @Override
    public boolean writeRecord(Path recordPath, InputStream in) {
        if (!writeEnabled) {
            return true;
        }
        System.out.println(recordPath.toString());
//        FileOutputStream output = new FileOutputStream(recordPath.toFile());
        return false;
    }

    @Override
    public OutputStream readRecord(Path recordPath) {
        return null;
    }
}
