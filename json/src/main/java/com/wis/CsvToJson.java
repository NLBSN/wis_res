package com.wis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: fan_tmp
 * @author: fan
 * @Date: Created in 2019/5/20 16:51
 * @Modified By:
 */
public class CsvToJson {

    private static String csvPath = "C:/Users/fan/Desktop/ceshi.txt";
    private static String jsonPaht = "C:/Users/fan/Desktop/OCFSt.json";

    public static void main(String args[]) throws Exception {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), "utf-8"));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jsonPaht), "utf-8"));
        String firstLine = bufferedReader.readLine().replaceAll("\"", "");
        String[] firstLines = firstLine.split(",", -1);
        JSONArray jsonArray = new JSONArray();
        String lastLine = "";
        int count = 1;
        while ((lastLine = bufferedReader.readLine()) != null) {
            count++;
            System.out.println(count + "          " + lastLine);
            Map map = new HashMap();
            String[] lastLines = lastLine.replaceAll("\"", "").split(",", -1);
            for (int i = 0; i < firstLines.length; i++) {
                map.put(firstLines[i], lastLines[i]);
            }
            jsonArray.add(JSON.toJSON(map));

        }
        bufferedWriter.write(jsonArray.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();

    }
}
