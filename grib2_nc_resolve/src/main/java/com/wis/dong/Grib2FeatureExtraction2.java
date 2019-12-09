package com.wis.dong;

import ucar.ma2.*;
import ucar.nc2.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: wis_res     要素提取
 * @author: fan
 * @Date: Created in 2019/4/21 11:06
 * @Modified By:
 */
public class Grib2FeatureExtraction2 {

    public static void main(String args[]) throws IOException {
        NetcdfFile dataFile = null;
        // String filename = "D:\\data\\dong\\W_NAFP_C_KWBC_20190903130000_P_gfs.t12z.pgrb2.0p25.f007.bin";
        String filename = "D:\\data\\dong\\W_NAFP_C_ECMF_20190901054730_P_C1D09010000090100011";
        String newFileName = "D:\\data\\dong\\ec.nc";

        try {
            dataFile = NetcdfFile.open(filename, null);
            List<Variable> variables = dataFile.getVariables();

            variables.forEach(tem->{
                if (tem.toString().contains("Total_cloud_cover_surface")){
                    System.out.println(tem);
                }
            });

            Variable lat = dataFile.findVariable("lat");

            float[] lat_data = (float[]) lat.read().copyToNDJavaArray();
            System.out.println("维度的类型：" + lat.getDataType() + "    " + lat.getSize());

            Variable lon = dataFile.findVariable("lon");
            float[] lon_data = (float[]) lon.read().copyToNDJavaArray();
            System.out.println("经度的类型：" + lon.getDataType() + "    " + lon.getSize());

            Variable time = dataFile.findVariable("time");
            double[] time_data = (double[]) time.read().copyToNDJavaArray();
            System.out.println("时间的类型：" + time.getDataType() + "     " + time.getSize());

            Variable dataVar = dataFile.findVariable("Total_cloud_cover_surface");
            float[][][] dataVar_data = (float[][][]) dataVar.read().copyToNDJavaArray();
            System.out.println("云的类型：" + dataVar.getDataType() + "     " + dataVar.getSize());

            // 有几个变量的值
            int[] shape = dataVar.getShape();
            System.out.print("云的变量里的类型的大小：");
            for (int tmp : shape) {
                System.out.print(tmp + "\t");
            }
            System.out.println();
            // 数据所在变量的类型，和它的长度：就是里面的数据怎么样才能定位找的到
            float[][][] dataIn = new float[shape[0]][shape[1]][shape[2]];
            int[] origin = new int[shape.length];
            Array read = dataVar.read(origin, shape);
            ArrayFloat.D3 dataArray = (ArrayFloat.D3) read;

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

            int time_l = (int) time.getSize();
            int lat_l = (int) lat.getSize();
            int lon_l = (int) lon.getSize();
            NetcdfFileWriter netcdfFileWriter = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, newFileName);
            //分别创建经度和纬度方向的维度，lon的宽度为width,lat高度为height,并命名为lon和lat
            Dimension timeDim = netcdfFileWriter.addDimension(null, "time", time_l);
            Dimension latDim = netcdfFileWriter.addDimension(null, "lat", lat_l);
            Dimension lonDim = netcdfFileWriter.addDimension(null, "lon", lon_l);
            //通过一维组合成二维array list
            ArrayList<Dimension> dims = new ArrayList<Dimension>();
            //注意这里add的顺序很重要,一定要和数据对应，如果把list顺序调换，那么二维的维度也会不同，相当于数学中的坐标系，第一个是x坐标，第二个是y坐标，x宽度为width,y高度为height
            dims.add(timeDim);
            dims.add(latDim);
            dims.add(lonDim);

            Variable vlat = netcdfFileWriter.addVariable(null, "lat", lat.getDataType(), "lat");
            List<Attribute> li_vlat = lat.getAttributes();
            for (Attribute attribute : li_vlat) {
                netcdfFileWriter.addVariableAttribute(vlat, attribute);
            }

            Variable vlon = netcdfFileWriter.addVariable(null, "lon", lon.getDataType(), "lon");
            List<Attribute> li_vlon = lon.getAttributes();
            for (Attribute attribute : li_vlon) {
                netcdfFileWriter.addVariableAttribute(vlon, attribute);
            }

            Variable vtime = netcdfFileWriter.addVariable(null, "time", time.getDataType(), "time");
            List<Attribute> li_vtime = time.getAttributes();
            for (Attribute attribute : li_vtime) {
                netcdfFileWriter.addVariableAttribute(vtime, attribute);
            }

            // 创建变量名称为cloudjudge的变量，对应的维度为dims,该dims为上面定义的一个ArrayList<Diminsion>，该list包含2个维度，经度和维度
            Variable v = netcdfFileWriter.addVariable(null, "Total_cloud_cover_surface", DataType.FLOAT, dims);
            List<Attribute> li_v = dataVar.getAttributes();
            for (Attribute attribute : li_v) {
                //new Attribute("missing_value",-1 )
                // if (attribute.getName().equals("missing_value")) {
                //     attribute = new Attribute("missing_value", 0);
                // }
                netcdfFileWriter.addVariableAttribute(v, attribute);
            }

            // fileWriter.addVariableAttribute(v, new Attribute("missing_value", -9999));
            // 使用create()来创建该文件，只有create之后才能在文件夹中显示该文件，并且写入数据
            netcdfFileWriter.create();
            //创建x和y方向上的变量。D1    代表一维
            ArrayDouble timevalues = new ArrayDouble.D1(time_l);
            ArrayDouble latvalues = new ArrayDouble.D1(lat_l);
            ArrayDouble lonvalues = new ArrayDouble.D1(lon_l);

            //创建网格上的变量，D2代表二维，用(width, height)来描述他的定义域，这个顺序必须和ArrayList<Dimension> 加载的顺序一一对应，否则会出错
            ArrayShort values = new ArrayShort.D3(time_l, lat_l, lon_l);
            for (int i = 0; i < time_l; i++) {
                timevalues.setDouble(i, time_data[i]);
            }
            for (int i = 0; i < lat_l; i++) {
                latvalues.setDouble(i, lat_data[i]);
            }
            for (int i = 0; i < lon_l; i++) {
                lonvalues.setDouble(i, lon_data[i]);
            }
            Index index = values.getIndex();
            for (int i = 0; i < time_l; i++) {
                for (int j = 0; j < lat_l; j++) {
                    for (int k = 0; k < lon_l; k++) {

                        float st = dataVar_data[i][j][k];
                        values.setFloat(index.set(i, j, k), st);
                    }
                }
            }

            netcdfFileWriter.write(vtime, timevalues);
            netcdfFileWriter.write(vlat, latvalues);
            netcdfFileWriter.write(vlon, lonvalues);
            netcdfFileWriter.write(v, values);
            netcdfFileWriter.close();


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