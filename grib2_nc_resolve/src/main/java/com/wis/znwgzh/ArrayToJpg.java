package com.wis.znwgzh;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * @Description: wis_res
 * @author: fan
 * @Date: Created in 2019/4/21 14:39
 * @Modified By:
 */
public class ArrayToJpg {

    @Test
    public void test11() {
        Double f = Double.valueOf((255 / 65534));
        System.out.println(f);
    }


    public static void main(String[] args) throws Exception {
        Float factor = 0.0038911099581896F;
        String txtName = "D:\\data\\ec\\NAFP_ECMF_FTM_TCC_LNO_GLB_20190421050000_00000-00300.TXT";
        String jpgName = "D:\\data\\ec\\test.png";

        // 获取文本中的二维数组
        Integer[][] matrix = txtToArray(txtName);

        BufferedImage image = new BufferedImage(1441, 2880, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        Color[][] arr = new Color[1441][2880];// 一个像素点
        String tmp = new String();
        try {
            // DecimalFormat df = new DecimalFormat("0.000000000");
            // df.format((float)a/b)
            for (int x = 0; x < matrix.length; x++) {
                for (int y = 0; y < matrix[0].length; y++) {
                    // int[] rgba = toThreeRgba(matrix[x][y]);
                    // arr[x][y] = new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
                    // double shuzhi = matrix[x][y] * 256.0077974791711D + 8388607.5D;
                    // int shuzhi1 = (int) shuzhi;
                    graphics.setColor(new Color(matrix[x][y] + 32767));
                    graphics.fillRect(x * 1, y * 1, 1, 1);
                }
            }
        } catch (Exception e) {
            System.out.println("-------" + tmp);
            e.printStackTrace();
        }
        ImageIO.write(image, "png", new FileOutputStream(new File("D:\\data\\ec\\test.jpg")));


    }

    public static Integer[][] txtToArray(String txtName) {
        Integer[][] array = new Integer[1441][2880];
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(txtName)), "utf-8"));
            String line;

            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",", -1);
                for (int j = 0; j < split.length; j++) {
                    if (Integer.parseInt(split[j]) == 32767) {
                        array[i][j] = 0;
                    } else {
                        array[i][j] = Integer.parseInt(split[j]);
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return array;
    }


    /**
     * @param color
     * @description 十进制转rgb值
     */
    public static int[] toRgba(int color) {
        int[] rgb = new int[4];
        // a 代表透明度
        int r, g, b, a;
        if (color != 0) {
            b = color & 0xff;
            g = (color >> 8) & 0xff;
            r = (color >> 16) & 0xff;
            a = 255;
        } else {
            b = 0;
            g = 0;
            r = 0;
            a = 0;
        }
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
        rgb[3] = a;
        return rgb;
    }

    public static int[] toThreeRgba(int color) {
        int[] rgb = new int[4];
        // a 代表亮度
        int r, g, b, a;
        if (color != 0) {
            b = color & 0xff;
            g = (color >> 8) & 0xff;
            r = (color >> 16) & 0xff;
            a = 255;
        } else {
            b = 0;
            g = 0;
            r = 0;
            a = 255;
        }
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
        rgb[3] = a;
        return rgb;
    }

    public static int[] toThreeFanRgba(int color) {
        int[] rgb = new int[4];
        // a 代表亮度
        int r, g, b, a;
        new Color(4.2f, 2.2f, 3.2f);
        if (color != 0) {
            b = color & 0xff;
            g = (color >> 8) & 0xff;
            r = (color >> 16) & 0xff;
            a = 255;
        } else {
            b = 0;
            g = 0;
            r = 0;
            a = 255;
        }
        rgb[0] = r;
        rgb[1] = g;
        rgb[2] = b;
        rgb[3] = a;
        return rgb;
    }


    //byte数组到图片
    public void byte2image(byte[] data, String path) {
        if (data.length < 3 || path.equals("")) return;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("Make Picture success,Please find image in " + path);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    @Test
    public void test() throws Exception {

        Random r = new Random();
        BufferedImage b = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        Graphics g = b.getGraphics();

        Color[][] arr = new Color[100][100];// 一个像素点

        for (int n = 0; n < 100; n++) {
            for (int m = 0; m < 100; m++) {
                arr[n][m] = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                g.setColor(arr[n][m]);
                g.fillRect(n * 10, m * 10, 10, 10);
            }
        }

        ImageIO.write(b, "png", new FileOutputStream(new File("D:\\data\\ec\\test.jpg")));
    }

    /**
     * @param hsb
     * @return
     * @description 将hsv颜色转换成rgb格式
     */
    public float[] hsb2rgb(float[] hsb) {
        float[] rgb = new float[3];
        //先令饱和度和亮度为100%，调节色相h
        for (int offset = 240, i = 0; i < 3; i++, offset -= 120) {
            //算出色相h的值和三个区域中心点(即0°，120°和240°)相差多少，然后根据坐标图按分段函数算出rgb。但因为色环展开后，红色区域的中心点是0°同时也是360°，不好算，索性将三个区域的中心点都向右平移到240°再计算比较方便
            float x = Math.abs((hsb[0] + offset) % 360 - 240);
            //如果相差小于60°则为255
            if (x <= 60) rgb[i] = 255;
                //如果相差在60°和120°之间，
            else if (60 < x && x < 120) rgb[i] = ((1 - (x - 60) / 60) * 255);
                //如果相差大于120°则为0
            else rgb[i] = 0;
        }
        //在调节饱和度s
        for (int i = 0; i < 3; i++)
            rgb[i] += (255 - rgb[i]) * (1 - hsb[1]);
        //最后调节亮度b
        for (int i = 0; i < 3; i++)
            rgb[i] *= hsb[2];
        return rgb;
    }

    //**将rgb色彩值转成16进制代码**
    public String convertRGBToHex(int r, int g, int b) {
        String rFString, rSString, gFString, gSString,
                bFString, bSString, result;
        int red, green, blue;
        int rred, rgreen, rblue;
        red = r / 16;
        rred = r % 16;
        if (red == 10) rFString = "A";
        else if (red == 11) rFString = "B";
        else if (red == 12) rFString = "C";
        else if (red == 13) rFString = "D";
        else if (red == 14) rFString = "E";
        else if (red == 15) rFString = "F";
        else rFString = String.valueOf(red);

        if (rred == 10) rSString = "A";
        else if (rred == 11) rSString = "B";
        else if (rred == 12) rSString = "C";
        else if (rred == 13) rSString = "D";
        else if (rred == 14) rSString = "E";
        else if (rred == 15) rSString = "F";
        else rSString = String.valueOf(rred);

        rFString = rFString + rSString;

        green = g / 16;
        rgreen = g % 16;

        if (green == 10) gFString = "A";
        else if (green == 11) gFString = "B";
        else if (green == 12) gFString = "C";
        else if (green == 13) gFString = "D";
        else if (green == 14) gFString = "E";
        else if (green == 15) gFString = "F";
        else gFString = String.valueOf(green);

        if (rgreen == 10) gSString = "A";
        else if (rgreen == 11) gSString = "B";
        else if (rgreen == 12) gSString = "C";
        else if (rgreen == 13) gSString = "D";
        else if (rgreen == 14) gSString = "E";
        else if (rgreen == 15) gSString = "F";
        else gSString = String.valueOf(rgreen);

        gFString = gFString + gSString;

        blue = b / 16;
        rblue = b % 16;

        if (blue == 10) bFString = "A";
        else if (blue == 11) bFString = "B";
        else if (blue == 12) bFString = "C";
        else if (blue == 13) bFString = "D";
        else if (blue == 14) bFString = "E";
        else if (blue == 15) bFString = "F";
        else bFString = String.valueOf(blue);

        if (rblue == 10) bSString = "A";
        else if (rblue == 11) bSString = "B";
        else if (rblue == 12) bSString = "C";
        else if (rblue == 13) bSString = "D";
        else if (rblue == 14) bSString = "E";
        else if (rblue == 15) bSString = "F";
        else bSString = String.valueOf(rblue);
        bFString = bFString + bSString;
        result = "#" + rFString + gFString + bFString;
        return result;

    }

    /**
     * RGB 转 Color
     *
     * @param c
     * @return
     */
    private static Color parseToColor(final String c) {
        Color convertedColor = Color.ORANGE;
        try {
            convertedColor = new Color(Integer.parseInt(c, 16));
        } catch (NumberFormatException e) {
            // codes to deal with this exception
        }
        return convertedColor;
    }
}


