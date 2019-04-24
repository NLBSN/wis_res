package utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * 读写图片
 *
 * @author taoww
 * 参考：http://www.360doc.com/content/16/0224/16/12500151_537069462.shtml
 */
public class RWImage {
    public static void main(String[] args) {
        //simpleRWImage();

        complexRWImage();
    }


    /**
     * 读写图片简单方式
     */
    public static void simpleRWImage() {
        try {
            //读取图片
            File f1 = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg");
            BufferedImage bi = ImageIO.read(f1);
            //将读取的图片写到本地
            File f2 = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\lihua.png");
            if (!f2.exists()) {
                f2.createNewFile();
            }
            ImageIO.write(bi, "png", f2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Java Image I/O API 提供了为编写复杂程序的能力。
     * 为了利用API的高级特性，应用程序应当直接使用类ImageReader和 ImageWriter读写图片
     */
    public static void complexRWImage() {
        try {
            /**********************读取图片*********************************/
            File f = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg");
            Iterator readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = (ImageReader) readers.next();
            /*
             * 获得了一个ImageReader对象，必须给它是指一个输入源。
             * 大部分 ImageReader对象可以从ImageInputStream类输入源读取数据，
             * ImageInputStream是Image I/O API定义的专用输入源
             */
            ImageInputStream iis = ImageIO.createImageInputStream(f);
            /*
             * 一旦有了输入源，可以把它与一个ImageReader对象关联起来.
             * 如果输入源文件包含多张图片，而程序不保证按顺序读取时，第二个参数应该设置为 false。
             * 对于那些只允许存储一张图片的文件格式，永远传递true是合理的
             */
            reader.setInput(iis, true);
            /*
             * 如果需要更多的控制，可以向read()方法传递一个ImageReadParam类型的参数。
             * 一个 ImageReadParam对象可以让程序更好的利用内存。
             * 它不仅允许指定一个感兴趣的区域，还 可以指定一个抽样因子，用于向下采样.
             * */
            ImageReadParam param = reader.getDefaultReadParam();
            int imageIndex = 0;
            int halfWidth = reader.getWidth(imageIndex) / 2;
            int halfHegiht = reader.getHeight(imageIndex) / 2;
            Rectangle rectangle = new Rectangle(0, 0, halfWidth, halfHegiht);
            param.setSourceRegion(rectangle);
            BufferedImage bi = reader.read(0, param);

            /**********************写图片*********************************/
            Iterator writes = ImageIO.getImageWritersByFormatName("png");
            ImageWriter imageWriter = (ImageWriter) writes.next();
            f = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\lihua.png");
            ImageOutputStream iops = ImageIO.createImageOutputStream(f);
            imageWriter.setOutput(iops);
            imageWriter.write(bi);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
