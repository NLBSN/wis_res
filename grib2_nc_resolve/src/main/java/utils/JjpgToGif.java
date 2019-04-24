package utils;

import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Description: wis_res
 * @author: fan
 * @Date: Created in 2019/4/24 21:21
 * @Modified By:
 */
public class JjpgToGif {
    public static void jpgToGif() {
        try {
            BufferedImage src = ImageIO.read(new File("d:/1.jpg")); // 读入文件1
            BufferedImage src1 = ImageIO.read(new File("d:/2.jpg")); // 读入文件2
            BufferedImage src2 = ImageIO.read(new File("d:/3.jpg")); // 读入文件3
            AnimatedGifEncoder e = new AnimatedGifEncoder();
            e.setRepeat(0);
            e.start("d:/laoma.gif");//生成gif图片位置名称
            e.setDelay(100);
            e.addFrame(src);
            e.setDelay(1000);
            e.addFrame(src1);
            e.setDelay(100);
            e.addFrame(src2);
            e.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
