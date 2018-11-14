package top.poul.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class IOUtilTest {

    @Test
    public void testGetString() throws Exception{
        String string = IOUtil.getString(new ByteArrayInputStream("123123".getBytes("UTF-8")));
        Assert.assertEquals("123123",string);
    }

}
