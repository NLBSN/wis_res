package com.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Pare_Bean {
    private static Properties prop = null;
    private static String filePath = null;
    private static String num = null;

    /**
     * @description 加载配置文件
     */
    static {
        InputStream in = null;
        try {
            // 包内读取
//			in = Pare_Bean.class.getClassLoader().getResourceAsStream("parse.properties");
        	File file = new File(new File("").getAbsolutePath() + "/parse.properties");
        	System.out.println(file);
            in = new FileInputStream(file);
            // jar包外读取
//             in = new FileInputStream("parse.properties");

            prop = new Properties();
            prop.load(in);
            filePath = prop.getProperty("FILE_PATH");
            num = prop.getProperty("NUM");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " "
                    + "属性文件加载完成，开始数据处理");
        }
    }

    public static Properties getProp() {
        return prop;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static String getNum() {
        return num;
    }

}
