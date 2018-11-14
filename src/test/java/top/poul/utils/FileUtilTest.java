package top.poul.utils;

import org.junit.Test;

import java.io.File;

public class FileUtilTest {


    @Test
    public void deleteFile() throws Exception{
        File file = new File("D:\\test\\rename\\id_card.jpg");
        String s = FileUtil.convertBase64(file);
    }

}
