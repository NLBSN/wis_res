package utils;

import com.madgag.gif.fmsware.GifDecoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * @Description: wis_res
 * @author: fan
 * @Date: Created in 2019/4/24 21:22
 * @Modified By:
 */
public class GifTojpg {
    private synchronized static void gifTojpg() throws IOException {
        GifDecoder decoder = new GifDecoder();
        InputStream is = new FileInputStream("d:/123.gif");
        if (decoder.read(is) != 0) {
            System.out.println("读取有错误");
            return;
        }
        is.close();
        System.out.println(" 帧是数量: " + decoder.getFrameCount());
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            BufferedImage frame = decoder.getFrame(i);
            int delay = decoder.getDelay(i);
            System.out.println("延迟时间: " + delay);
            OutputStream out = new FileOutputStream("d:/" + i + "bb.jpg");
            ImageIO.write(frame, "jpeg", out);// 将frame 按jpeg格式 写入out中
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            out.flush();
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {
        URL resource = Image.class.getResource("./Kitty_xs013.gif");
        BufferedImage sourceImage = ImageIO.read(resource);
        BufferedImage dstImage = null;
        AffineTransform transform = AffineTransform.getScaleInstance(0.5, 0.5);// 返回表示缩放变换的变换
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        dstImage = op.filter(sourceImage, null);

        /********** save到本地 *****************/
        try {
            ImageIO.write(dstImage, "png", new File("D:\\a.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /********** save end *****************/

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Source Transform", new JLabel(new ImageIcon(sourceImage)));
        tabbedPane.add("Affine Transform", new JLabel(new ImageIcon(dstImage)));

        JFrame jframe = new JFrame();
        jframe.setSize(800, 600);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().add(tabbedPane);
        jframe.setVisible(true);

    }

}
