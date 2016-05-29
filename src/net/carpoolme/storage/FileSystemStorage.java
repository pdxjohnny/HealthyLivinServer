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
        baseDirHandle = createDir(baseDir);
        enableWrite();
    }

    public FileSystemStorage(Path mBaseDir) throws FileSystemException {
        baseDir = mBaseDir;
        baseDirHandle = createDir(baseDir);
        enableWrite();
    }

    // Makes sure we can read and write from the baseDir directory
    private static File createDir(Path dir) throws FileSystemException {
        File dirHandle = dir.toFile();
        if (!dirHandle.exists()) {
            if (!dirHandle.mkdirs()) throw new FileSystemException(dir.toAbsolutePath().toString());
        } else if (!dirHandle.isDirectory()) {
            throw new DirectoryNotEmptyException(dir.toAbsolutePath().toString());
        }
        return dirHandle;
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
            if (files != null) {
                for (File file : files) {
                    if (!destroyRecursive(file)) {
                        return false;
                    }
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
        return true;
    }

    @Override
    public boolean writeRecord(Path recordPath, InputStream in) {
        if (!writeEnabled) {
            return true;
        }
        Path filePath = Paths.get(baseDir.toString(), recordPath.toString());
        Path fileDirPath = filePath.getParent();
        try {
            createDir(fileDirPath);
        } catch (FileSystemException e) {
            return false;
        }
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(filePath.toFile());
        } catch (FileNotFoundException e) {
            return false;
        }
        byte[] buffer;
        final int maxBuffer = 4095;
        int shouldRead;
        int read = 1;
        try {
            while (read > 0) {
                shouldRead = in.available();
                if (shouldRead > maxBuffer) {
                    shouldRead %= maxBuffer;
                    ++shouldRead;
                }
                buffer = new byte[shouldRead];
                read = in.read(buffer);
                if (shouldRead < 1) {
                    break;
                }
                outputStream.write(buffer);
            }
        } catch(IOException ignored) {
            return false;
        }
        return true;
    }

    @Override
    public InputStream readRecord(Path recordPath) {
        Path filePath = Paths.get(baseDir.toString(), recordPath.toString());
        try {
            return new FileInputStream(filePath.toFile());
        } catch (FileNotFoundException ignored) {}
        return null;
    }

    @Override
    public InputStream[] allRecords() {
        String[] allFiles = baseDirHandle.list();
        InputStream[] allRecords = new InputStream[allFiles.length];
        for (int i = 0; i < allFiles.length; ++i) {
            allRecords[i] = readRecord(Paths.get(allFiles[i]));
        }
        return allRecords;
    }

    @Override
    public Storage downLevel(Path dir) {
        dir = Paths.get(baseDir.toString(), dir.toString());
        try {
            return new FileSystemStorage(dir);
        } catch (FileSystemException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }
}
