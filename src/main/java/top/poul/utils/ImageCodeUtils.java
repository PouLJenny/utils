package top.poul.utils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 *  图片验证码生成工具
 */
public class ImageCodeUtils {

    private ImageCodeUtils(){}

    private static final int WIDTH = 90;// 定义图片的width
    private static final int HEIGHT = 20;// 定义图片的height
    private static final int CODE_COUNT = 4;// 定义图片上显示验证码的个数
    private static final int xx = 15;
    private static final int FONT_HEIGHT = 18;
    private static final int CODE_Y = 16;
    private static final char[] CODE_SEQUENCE = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
            , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * 生成一个map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     * @return
     */
    public static RandomIamgeCode generateCodeAndPic() {
        return generateCodeAndPic(WIDTH,HEIGHT,CODE_COUNT);
    }


    /**
     * 生成一个map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     * @param width         定义图片的width
     * @param height        定义图片的height
     * @param codeCount     定义图片上显示验证码的个数
     * @return
     */
    public static RandomIamgeCode generateCodeAndPic(int width,int height,int codeCount) {
        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.BOLD, FONT_HEIGHT);
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String code = String.valueOf(CODE_SEQUENCE[random.nextInt(CODE_SEQUENCE.length)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, CODE_Y);

            // 将产生的四个随机数组合在一起。
            randomCode.append(code);
        }
        //存放验证码
        //存放生成的验证码BufferedImage对象
        return new RandomIamgeCode(randomCode.toString(),buffImg);
    }


    public static class RandomIamgeCode {

        /**
         *  图片验证码的值
         */
        private String code;
        /**
         *  图片
         */
        private BufferedImage bufferedImage;

        private String imageType = "png";

        public RandomIamgeCode() {
        }

        public RandomIamgeCode(String code, BufferedImage bufferedImage) {
            this.code = code;
            this.bufferedImage = bufferedImage;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        public void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }

        /**
         * 获取图片验证码的base64字符串
         * @return
         */
        public String imageBase64String() throws IOException{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, imageType, baos);
            baos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }

    }

    public static void main(String[] args) throws Exception {
        //创建文件输出流对象
        RandomIamgeCode randomIamgeCode = generateCodeAndPic(100,25,6);
        System.out.println("验证码的值为："+randomIamgeCode.getCode());

        System.out.println(randomIamgeCode.imageBase64String());
    }
}
