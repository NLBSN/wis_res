package com.wis.znwgzh;

import ucar.ma2.*;
import ucar.nc2.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: wis_res    这个里面包含了读取和创建nc文件两块
 * @author: fan
 * @Date: Created in 2019/4/21 11:06
 * @Modified By:
 */
public class Simple_xy_rd_nc {

    public static void main(String args[]) throws IOException {
        NetcdfFile dataFile = null;
        String filename = "D:\\data\\ec\\W_NAFP_C_ECMF_20190420055105_P_C1D04200000042009001.nc";
        String newFileName = "D:\\data\\ec\\W_NAFP_C_ECMF_20190420055105_P_C1D04200000042009001.TXT";
        try {
            dataFile = NetcdfFile.open(filename, null);
            // System.out.println(dataFile.getDetailInfo());
            Variable lat = dataFile.findVariable("latitude");
            System.out.println("维度的类型：" + lat.getDataType());
            System.out.println(lat.getSize());

            Variable lon = dataFile.findVariable("longitude");
            System.out.println("经度的类型：" + lon.getDataType());

            Variable time = dataFile.findVariable("time");
            System.out.println("时间的类型：" + time.getDataType());

            Variable dataVar = dataFile.findVariable("u10");
            short[][][] dataArray = (short[][][]) dataVar.read().copyToNDJavaArray();
            System.out.println("数据的类型：" + dataVar.getDataType());
            System.out.println("数据的维度：" + dataVar.getDimensionsAll());
            System.out.println(dataVar.getDimension(0).getLength());
            System.out.println(dataVar.getDimension(1).getLength());
            System.out.println(dataVar.getDimension(2).getLength());
            // int[] shape = dataVar.getShape();
            // System.out.println(dataVar.getShape().length);
            // int[] origin = new int[3];

            // ArrayInt.D2 dataArray = (ArrayInt.D2) dataVar.read(origin, shape);


            // final int NX = 6;
            // final int NY = 12;
            // Check the values.
            // assert shape[0] == NX;
            // assert shape[1] == NY;
            // int[][] dataIn = new int[NX][NY];

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(newFileName)));
            // bufferedWriter.write("[");
            for (int t = 0; t < 1; t++) {
                for (int i = 0; i < 1441; i++) {
                    for (int j = 0; j < 2880; j++) {
                        System.out.print(dataArray[t][i][j] + "\t");
                        bufferedWriter.write(dataArray[t][i][j] + ",");
                        bufferedWriter.newLine();
                    }
                    System.out.println();
                    // bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
            // bufferedWriter.write("]");
            bufferedWriter.close();
            // double[][] data_data = (double[][]) data.read().copyToNDJavaArray();
            // System.out.println("这个变量的位置：" + data.getDatasetLocation());
            // System.out.println("这个变量的描述：" + data.getDescription());
            // System.out.println("这个变量的维度：" + data.getDimensionsString());
            // System.out.println("这个变量的0位time长度：" + data.getDimension(0));
            // System.out.println("这个变量的1位lat长度：" + data.getDimension(1));
            // System.out.println("这个变量的2位lon长度：" + data.getDimension(2));
            // System.out.println("这个变量的3位：" + data.getDimension(3));
            // System.out.println("这个变量的属性：" + data.getAttributes());
            // System.out.println("这个变量的名字和维度：" + data.getNameAndDimensions());
            // System.out.println("这个变量的单位：" + data.getUnitsString());
            // System.out.println("这个变量的单位："+data.read()); // 待测试
            // System.out.print("此变量0的数据为：");
            // for (double str : data_data[0]) {
            //     System.out.print(str + "\t");
            // }
            // System.out.println();
            // System.out.print("此变量1的数据为：");
            // for (double str : data_data[0]) {
            //     System.out.print(str + "\t");
            // }
            // System.out.println();
            // System.out.println("===" + data.isVariableLength());
            // System.out.println("time长度为：" + data_data.length);
            // System.out.println("lat长度为：" + data_data[0].length);

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