package top.poul.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 文件和目录的工具类
 * @author 杨霄鹏
 * @version 1.0, 2017-01-06
 * @since
 */

public class FileUtil {


    /**
     * 删除指定目录中的空文件夹
     * @param filePath
     * @return
     */
    public static List<String> removeEmptyDirectory(String filePath) {
        File file = new File(filePath);
        List<String> removeFile = new ArrayList<String>(); 
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length <= 0) {
                List<String> delete = delete(file);
                removeFile.addAll(delete);
            } else {
                for (File f : listFiles) {
                    if (f.isDirectory()) {
                        List<String> removeEmptyDirectory = removeEmptyDirectory(f.getAbsolutePath());
                        if (!removeEmptyDirectory.isEmpty()) {
                            removeFile.addAll(removeEmptyDirectory);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("参数不是文件夹" + filePath);
        }
        return removeFile;
    } 
    
    /**
     * 删除文件夹动作
     * @param file
     * @return
     */
    private static List<String> delete(File file) {
        List<String> removeFile = new ArrayList<String>(); 
        file.delete();
        removeFile.add(file.getAbsolutePath() + "\r\n");
        File parentFile = file.getParentFile();
        File[] listFiles = parentFile.listFiles();
        if(listFiles == null || listFiles.length <= 0) {
            List<String> delete = delete(parentFile);
            removeFile.addAll(delete);
        }
        return removeFile;
    }
    
    /**
     * 将文件转换成字节数组
     * @param filePath
     * @return
     * @throws IOException
     * @since 2017年7月2日 下午4:20:31
     */
    public static byte[] InputStream2ByteArray(String filePath) throws IOException {
	    InputStream in = new FileInputStream(filePath);
	    byte[] data = IOUtil.bytes(in);
	    return data;
	}
	 
	/**
	 * 读取文本信息
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static String read(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        String string = IOUtil.getString(fileInputStream);
        return string;
    }
	
	
	/**
	 * 获取文件的扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtension(String filename) {
		   int index = filename.lastIndexOf('.');
		   if (index  < 1) {
		     return "";
		   } else {
		     return filename.substring(index + 1);
		   }
	}

	public static void deleteFile(String filePath) {
        deleteFile(new File(filePath));
    }

    /**
     * 删除文件
     * 如果是文件夹则删除整个文件夹
     * @param file
     */
	public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        file.setWritable(true);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files ==null || files.length <= 0) {
                file.delete();
                return;
            }
            for (File f : files) {
                deleteFile(f);
            }
        }
        file.delete();
    }

    /**
     * 删除文件 只删除文件 不能传文件夹
     * @param file
     */
    public static void deleteDocument(File file) {
        if(!file.exists()) {
            return;
        }
        file.setWritable(true);
        int i = 0;
        do {
            deleteFile(file);
            i++;
        } while (file.exists() && i <= 20);

        if (file.exists()) {
            // 文件删除失败
            System.out.println("文件删除失败");
        } else {
            System.out.println("文件删除成功，尝试删除次数" + i);
        }
    }

    /**
     * 复制文件
     * @param source
     * @param dest
     */
    public static void copyFile(File source,File dest) throws IOException{
        File parentFile = dest.getParentFile();
        if (parentFile == null || !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try(FileChannel inputChannel = new FileInputStream(source).getChannel();
            FileChannel outputChannel = new FileOutputStream(dest).getChannel()){

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        }
    }

    /**
     *  保存文件 如果存在先删除在保存
     */
    public static void saveFile(String dirPath, String fileName,InputStream is)  throws IOException{
        String filePath = dirPath + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        OutputStream os = null;
        try {
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(dirPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            os = new FileOutputStream(filePath);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } finally {
            if (os != null)
                os.close();
            if (is != null)
                is.close();
        }

    }

    public static void saveFile(File file,InputStream is)  throws IOException{
        String absolutePath = file.getAbsolutePath();
        int i = absolutePath.lastIndexOf(File.separator);
        String dirPath = absolutePath.substring(0, i);
        String fileName = absolutePath.substring(i + 1);
        saveFile(dirPath,fileName,is);
    }

    public static void saveBase64File(File file,String base64) throws IOException{
        byte[] decode = Base64.getDecoder().decode(base64);
        saveFile(file,new ByteArrayInputStream(decode));
    }


    /**
     * 转换成Base64编码
     * @param is
     * @return
     */
    public static String convertBase64(InputStream is) throws IOException {
        byte[] bytes = IOUtil.bytes(is);
        String encode = Base64.getEncoder().encodeToString(bytes);
        return encode;
    }

    /**
     * 转换成Base64编码
     * @param file
     * @return
     */
    public static String convertBase64(File file) throws IOException {
        return convertBase64(new FileInputStream(file));
    }

    /**
     * 转换成Base64编码
     * @param filePath
     * @return
     */
    public static String convertBase64(String filePath) throws IOException {
        return convertBase64(new File(filePath));
    }

    public static void main(String[] args) throws IOException{
        deleteFile("D:\\test");
        File IDPictureF = new File("D:\\test\\rename\\id_card.png");

        String s = convertBase64(new FileInputStream(IDPictureF));
        System.out.println(s);

    }

}
