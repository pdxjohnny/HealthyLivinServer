package net.carpoolme.storage;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * Created by John Andersen on 5/28/16.
 */
public class FileSystemStorageTest {

    @Test
    public void testStart() throws Exception {
        Storage storage = new FileSystemStorage(Paths.get("tmp", "FileSystemStorageTest"));
        Assert.assertEquals(true, storage.start());
        storage.destroy();
    }

    @Test
    public void testStop() throws Exception {
        Storage storage = new FileSystemStorage(Paths.get("tmp", "FileSystemStorageTest"));
        Assert.assertEquals(true, storage.stop());
        storage.destroy();
    }

    @Test
    public void testEnableWrite() throws Exception {

    }

    @Test
    public void testDisableWrite() throws Exception {

    }

    @Test
    public void testWriteRecord() throws Exception {

    }

    @Test
    public void testReadRecord() throws Exception {

    }
}