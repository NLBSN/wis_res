package com.wis.znwgzh;

import ucar.ma2.ArrayShort;
import ucar.nc2.*;

import java.io.*;
import java.io.FileWriter;

/**
 * @Description: wis_res    这个里面包含了读取和创建nc文件两块
 * @author: fan
 * @Date: Created in 2019/4/21 11:06
 * @Modified By:
 */
public class Simple_xy_rd_nc {

    public static void main(String args[]) throws IOException {
        NetcdfFile dataFile = null;
        String filename = "D:\\data\\ec\\NAFP_ECMF_FTM_TCC_LNO_GLB_20190421050000_00000-00300.NC";
        String newFileName = "D:\\data\\ec\\NAFP_ECMF_FTM_TCC_LNO_GLB_20190421050000_00000-00300.TXT";

        try {
            dataFile = NetcdfFile.open(filename, null);

            Variable lat = dataFile.findVariable("latitude");
            System.out.println("维度的类型：" + lat.getDataType());

            Variable lon = dataFile.findVariable("longitude");
            System.out.println("经度的类型：" + lon.getDataType());

            Variable time = dataFile.findVariable("time");
            System.out.println("时间的类型：" + time.getDataType());

            Variable dataVar = dataFile.findVariable("tcc");

            // 数据所在变量的类型，和它的长度：就是里面的数据怎么样才能定位找的到
            short[][][] dataIn = new short[1][1441][2880];
            // 有几个变量的值
            int[] shape = dataVar.getShape();
            int[] origin = new int[shape.length];

            ArrayShort.D3 dataArray = (ArrayShort.D3) dataVar.read(origin, shape);

            // Check the values.
            // assert shape[0] == NX;
            // assert shape[1] == NY;
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(newFileName)), "utf-8"));

            for (int z = 0; z < shape[0]; z++) {
                for (int x = 0; x < shape[1]; x++) {
                    for (int y = 0; y < shape[2]; y++) {
                        dataIn[z][x][y] = dataArray.get(z, x, y);
                        bufferedWriter.write(dataIn[z][x][y] + "");
                        if (y < shape[2] - 1) bufferedWriter.write(",");
                    }
                    bufferedWriter.newLine();
                }
                bufferedWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dataFile != null)
                try {
                    dataFile.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }
        System.out.println("*** SUCCESS reading example file simple_xy.nc!");
    }
}