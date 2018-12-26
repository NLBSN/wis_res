package com.parse_report;

import com.report.utils.IOTxt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Create by JIUN 2018/7/14
 */
public class Main {

    /**
     * @param args
     * @throws Exception
     * @description 主程序入口
     */
    public static void main(String[] args) throws Exception {

        // new ParseUrl().cycle();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("config.properties"), "gbk"));

        String tmpLine;
        StringBuffer stringBuffer = new StringBuffer();
        while ((tmpLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(tmpLine);
            stringBuffer.append("\n");
        }
        new IOTxt().analys(stringBuffer);
        // System.out.println(stringBuffer);
        bufferedReader.close();
    }

}
