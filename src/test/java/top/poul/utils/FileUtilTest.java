package top.poul.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class FileUtilTest {


    @Test
    public void deleteFile() throws Exception{
        File file = new File("D:\\test\\rename\\id_card.jpg");
        String s = FileUtil.convertBase64(file);
    }


    @Test
    public void testFileList() throws Exception {
        String filePath = "/home/poul/";
        File file = new File(filePath);
        if (!file.isDirectory()) {
            throw new RuntimeException("请输入文件夹");
        }

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                continue;
            }
            String fileName = f.getName();
            if (fileName.endsWith(".obj")) {
                System.out.println(fileName);
            }
        }

    }

}
