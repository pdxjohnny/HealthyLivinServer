package net.carpoolme.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by John Andersen on 6/2/16.
 */
public class ArgumentParserTest {
    private static final String[] argv = new String[] {"-flag", "--int", "42", "--arg", "argValue", "--arg", "argValue2"};

    @Test
    public void testParseArgs() throws Exception {
        new ArgumentParser(argv).parseArgs();
    }

    @Test
    public void testGetBoolean() throws Exception {
        Assert.assertEquals(true, new ArgumentParser(argv).parseArgs().getBoolean("flag"));
        Assert.assertEquals(false, new ArgumentParser(argv).parseArgs().getBoolean("flag not present"));
    }

    @Test
    public void testGetInteger() throws Exception {
        Assert.assertEquals(42, new ArgumentParser(argv).parseArgs().getInteger("int"));
        Assert.assertEquals(0, new ArgumentParser(argv).parseArgs().getInteger("int not present"));
    }

    @Test
    public void testGetString() throws Exception {
        Assert.assertEquals("argValue", new ArgumentParser(argv).parseArgs().getString("arg"));
        Assert.assertEquals("", new ArgumentParser(argv).parseArgs().getString("arg not present"));
    }

    @Test
    public void testGetStringArray() throws Exception {
        Assert.assertArrayEquals(new String[] {"argValue", "argValue2"}, new ArgumentParser(argv).parseArgs().getStringArray("arg"));
        Assert.assertArrayEquals(new String[0], new ArgumentParser(argv).parseArgs().getStringArray("arg not found"));
    }
}
