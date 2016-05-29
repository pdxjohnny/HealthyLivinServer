package net.carpoolme.storage;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by John Andersen on 5/28/16.
 */
public class FileSystemStorageTest {
    @Test
    public void testStart() throws Exception {
        Storage storage = new FileSystemStorage(Paths.get("tmp", "FileSystemStorageTest"));
        Assert.assertEquals(true, storage.start());
        Assert.assertEquals(true, storage.destroy());
    }

    @Test
    public void testStop() throws Exception {
        Storage storage = new FileSystemStorage(Paths.get("tmp", "FileSystemStorageTest"));
        Assert.assertEquals(true, storage.stop());
        Assert.assertEquals(true, storage.destroy());
    }

    @Test
    public void testReadWrite() throws Exception {
        Storage storage = new FileSystemStorage(Paths.get("tmp", "FileSystemStorageTest"));
        Assert.assertEquals(true, storage.start());
        Path recordPath = Paths.get("table", "record");
        final int stringSize = 10000;
        String shouldBe = "";
        for (int i = 0; i < stringSize; ++i) {
            shouldBe += "A";
        }
        Assert.assertEquals(true, storage.writeRecord(recordPath, new ByteArrayInputStream(shouldBe.getBytes("UTF-8"))));
        InputStream in = storage.readRecord(recordPath);
        Assert.assertNotEquals(null, in);
        Scanner scanner = new Scanner(in).useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        Assert.assertEquals(shouldBe, string);
        Assert.assertEquals(true, storage.destroy());
    }
}