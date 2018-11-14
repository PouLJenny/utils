package top.poul.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class ImageUtil {

	
	/**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @author Poul.Y 2015-01-26
     * @param path 图片路径
     * @return
     */
    public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        InputStream in = null;
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        // 对字节数组Base64编码
        return Base64.getEncoder().encodeToString(data);// 返回Base64编码过的字节数组字符串
    }
	
    
    
    public static ImageInfo resolution(String url) throws IOException {
		URL u = new URL(url);
		BufferedImage bi = ImageIO.read(u);
		ImageInfo imageInfo = new ImageInfo();
		
		int width = bi.getWidth();// 宽
		int height = bi.getHeight(); // 高
		
		
		imageInfo.setHeight(height);
		imageInfo.setWidth(width);
		
		
		return imageInfo;
    }
    
    
    /**
     * 图片信息
     * @author 杨霄鹏
     * @since 2018-01-20 11:04:32
     */
    public static class ImageInfo {
    	
    	/**
    	 * 宽 像素
    	 */
    	private Integer width;
    	
    	/**
    	 * 高 像素
    	 */
    	private Integer height;

		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}
    	
    	
    }
    
}
