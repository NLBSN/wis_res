package com.wis.znwgzh;

import ucar.ma2.*;
import ucar.nc2.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: wis_res    这个里面包含了读取和创建nc文件两块
 * @author: fan
 * @Date: Created in 2019/4/21 11:06
 * @Modified By:
 */
public class Simple_xy_rd_wr {

    public static void main(String args[]) throws IOException {
        NetcdfFile dataFile = null;
        String filename = "D:\\data\\ec\\W_NAFP_C_ECMF_20190420065705_P_C1D04200000043000001.grib";

        try {
            dataFile = NetcdfFile.open(filename, null);
            System.out.println(dataFile.getDetailInfo());// 可以查看到一些详细的信息，比如坐标轴上的数值
            System.out.println("------------------------------------------------------------------------");
            System.out.println(dataFile.getVariables());// 获取变量
            System.out.println("========================================================================");
            System.out.println(dataFile.getDimensions()); // 获取维
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println(dataFile.getGlobalAttributes());// 获取属性
            System.out.println("************************************************************************");

            Variable lat = dataFile.findVariable("lat");
            float[] lat_data = (float[]) lat.read().copyToNDJavaArray();
            System.out.println("这个变量的位置：" + lat.getDatasetLocation());
            System.out.println("这个变量的描述：" + lat.getDescription());
            System.out.println("这个变量的维度：" + lat.getDimensionsString());
            System.out.println("获得维度的大小"+lat.getShape().length);
            System.out.println("这个变量的0：" + lat.getDimension(0));
            System.out.println("这个变量的属性：" + lat.getAttributes());
            System.out.println("这个变量的名字和维度：" + lat.getNameAndDimensions());
            System.out.println("-------------------------------");
            System.out.print("此变量0的数据为：");
            for (float str : lat_data) {
                System.out.print(str + "\t");
            }

            Variable lon = dataFile.findVariable("lon");
            // v.read().reduce(0) 是去掉一个维度 ，我一般三维转二维会用到这个
            float[] lon_data = (float[]) lon.read().copyToNDJavaArray();

            Variable time = dataFile.findVariable("time");
            double[] time_data = (double[]) time.read().copyToNDJavaArray();
            // for (double str : time_data) {
            //     System.out.println(str);

            // }
            Variable metre_U_wind_component_surface = dataFile.findVariable("10_metre_U_wind_component_surface");
            float[][][] metre_U_wind_component_surface_data = (float[][][]) metre_U_wind_component_surface.read().copyToNDJavaArray();
            System.out.println("这个变量的位置：" + metre_U_wind_component_surface.getDatasetLocation());
            System.out.println("这个变量的描述：" + metre_U_wind_component_surface.getDescription());
            System.out.println("这个变量的维度：" + metre_U_wind_component_surface.getDimensionsString());
            System.out.println("这个变量的0位time长度：" + metre_U_wind_component_surface.getDimension(0));
            System.out.println("这个变量的1位lat长度：" + metre_U_wind_component_surface.getDimension(1));
            System.out.println("这个变量的2位lon长度：" + metre_U_wind_component_surface.getDimension(2));
            System.out.println("这个变量的3位：" + metre_U_wind_component_surface.getDimension(3));
            System.out.println("这个变量的属性：" + metre_U_wind_component_surface.getAttributes());
            System.out.println("这个变量的名字和维度：" + metre_U_wind_component_surface.getNameAndDimensions());
            System.out.println("这个变量的单位：" + metre_U_wind_component_surface.getUnitsString());
            // System.out.println("这个变量的单位："+metre_U_wind_component_surface.read()); // 待测试
            System.out.print("此变量0的数据为：");
            for (float str : metre_U_wind_component_surface_data[0][0]) {
                System.out.print(str + "\t");
            }
            System.out.println();
            System.out.print("此变量1的数据为：");
            for (float str : metre_U_wind_component_surface_data[0][1]) {
                System.out.print(str + "\t");
            }
            System.out.println();
            System.out.println("===" + metre_U_wind_component_surface.isVariableLength());
            System.out.println("time长度为：" + metre_U_wind_component_surface_data.length);
            System.out.println("lat长度为：" + metre_U_wind_component_surface_data[0].length);
            System.out.println("lon长度为：" + metre_U_wind_component_surface_data[0][0].length);
            System.out.println("[0,0,0]为：" + metre_U_wind_component_surface_data[0][0][0]);

            int timeLenght = time_data.length;
            int latLenght = lat_data.length;
            int lonLenght = lon_data.length;
            NetcdfFileWriter netcdfFileWriter = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, "D:\\data\\ec\\W_NAFP_C_ECMF_20190420065705_P_C1D04200000043000001_lat.nc");
            //分别创建经度和纬度方向的维度，lon的宽度为width,lat高度为height,并命名为lon和lat
            Dimension timeDim = netcdfFileWriter.addDimension(null, "time", timeLenght);
            Dimension latDim = netcdfFileWriter.addDimension(null, "lat", latLenght);
            Dimension lonDim = netcdfFileWriter.addDimension(null, "lon", lonLenght);
            //通过一维组合成二维array list
            ArrayList<Dimension> dims = new ArrayList<Dimension>();
            //注意这里add的顺序很重要,一定要和数据对应，如果把list顺序调换，那么二维的维度也会不同，相当于数学中的坐标系，第一个是x坐标，第二个是y坐标，x宽度为width,y高度为height
            dims.add(timeDim);
            dims.add(latDim);
            dims.add(lonDim);

            Variable vlat = netcdfFileWriter.addVariable(null, "lat", DataType.FLOAT, "lat");
            List<Attribute> li_vlat = lat.getAttributes();
            for (Attribute attribute : li_vlat) {
                netcdfFileWriter.addVariableAttribute(vlat, attribute);
            }

            Variable vlon = netcdfFileWriter.addVariable(null, "lon", DataType.FLOAT, "lon");
            List<Attribute> li_vlon = lon.getAttributes();
            for (Attribute attribute : li_vlon) {
                netcdfFileWriter.addVariableAttribute(vlon, attribute);
            }

            Variable vtime = netcdfFileWriter.addVariable(null, "time", DataType.DOUBLE, "time");
            List<Attribute> li_vtime = time.getAttributes();
            for (Attribute attribute : li_vtime) {
                netcdfFileWriter.addVariableAttribute(vtime, attribute);
            }

            // 创建变量名称为cloudjudge的变量，对应的维度为dims,该dims为上面定义的一个ArrayList<Diminsion>，该list包含2个维度，经度和维度
            Variable v = netcdfFileWriter.addVariable(null, "metre_U_wind_component_surface", DataType.FLOAT, dims);
            List<Attribute> li_v = metre_U_wind_component_surface.getAttributes();
            for (Attribute attribute : li_v) {
                //new Attribute("missing_value",-1 )
                if (attribute.getName().equals("missing_value")) {
                    attribute = new Attribute("missing_value", 0);
                }
                netcdfFileWriter.addVariableAttribute(v, attribute);
            }

            // fileWriter.addVariableAttribute(v, new Attribute("missing_value", -9999));
            // 使用create()来创建该文件，只有create之后才能在文件夹中显示该文件，并且写入数据
            netcdfFileWriter.create();
            //创建x和y方向上的变量。D1    代表一维
            ArrayDouble timevalues = new ArrayDouble.D1(timeLenght);
            ArrayDouble latvalues = new ArrayDouble.D1(latLenght);
            ArrayDouble lonvalues = new ArrayDouble.D1(lonLenght);

            //创建网格上的变量，D2代表二维，用(width, height)来描述他的定义域，这个顺序必须和ArrayList<Dimension> 加载的顺序一一对应，否则会出错
            ArrayShort values = new ArrayShort.D3(timeLenght, latLenght, lonLenght);
            for (int i = 0; i < timeLenght; i++) {
                timevalues.setDouble(i, time_data[i]);
            }
            for (int i = 0; i < latLenght; i++) {
                latvalues.setDouble(i, lat_data[i]);
            }
            for (int i = 0; i < lonLenght; i++) {
                lonvalues.setDouble(i, lon_data[i]);
            }
            Index index = values.getIndex();
            for (int i = 0; i < timeLenght; i++) {
                for (int j = 0; j < latLenght; j++) {
                    for (int k = 0; k < lonLenght; k++) {

                        float st = metre_U_wind_component_surface_data[i][j][k];
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