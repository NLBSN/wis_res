package com.wis.dong;

import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayShort;
import ucar.ma2.Index;
import ucar.nc2.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: wis_res
 * @author: fan
 * @Date: Created in 2019/12/6 10:05
 * @Modified By:
 */
public class Utils {
    private HashMap<File, LinkedList<File>> hashMap = new HashMap();

    /**
     * @param filename
     * @param newFileName
     * @param element
     */
    public void copyGrib2OrNc(String filename, String newFileName, String element) {
        NetcdfFile dataFile = null;
        NetcdfFileWriter netcdfFileWriter = null;
        try {
            dataFile = NetcdfFile.open(filename, null);
            Variable lat = dataFile.findVariable("lat");
            Variable lon = dataFile.findVariable("lon");
            Variable time = dataFile.findVariable("time");
            Variable dataVar = dataFile.findVariable(element);

            if (dataVar == null) {
                System.out.printf("此文件没有处理：%s\n", filename);
                return;
            }
            // 有几个变量的值
            int[] shape = dataVar.getShape();
            // System.out.print("云的变量里的类型的大小：");
            // for (int tmp : shape) {
            //     System.out.print(tmp + "\t");
            // }
            // System.out.println();

            netcdfFileWriter = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, newFileName);
            //分别创建经度和纬度方向的维度，lon的宽度为width,lat高度为height,并命名为lon和lat
            Dimension timeDim = netcdfFileWriter.addDimension(null, "time", shape[0]);
            Dimension latDim = netcdfFileWriter.addDimension(null, "lat", shape[1]);
            Dimension lonDim = netcdfFileWriter.addDimension(null, "lon", shape[2]);
            //通过一维组合成二维array list
            ArrayList<Dimension> dims = new ArrayList<Dimension>();
            //注意这里add的顺序很重要,一定要和数据对应，如果把list顺序调换，那么二维的维度也会不同，相当于数学中的坐标系，第一个是x坐标，第二个是y坐标，x宽度为width,y高度为height
            dims.add(timeDim);
            dims.add(latDim);
            dims.add(lonDim);

            Variable vlat = netcdfFileWriter.addVariable(null, "lat", lat.getDataType(), "lat");
            float[] lat_data = (float[]) lat.read().copyToNDJavaArray();
            float[] lon_data = (float[]) lon.read().copyToNDJavaArray();
            double[] time_data = (double[]) time.read().copyToNDJavaArray();
            float[][][] dataVar_data = (float[][][]) dataVar.read().copyToNDJavaArray();

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
            Variable v = netcdfFileWriter.addVariable(null, element, dataVar.getDataType(), dims);
            List<Attribute> li_v = dataVar.getAttributes();
            for (Attribute attribute : li_v) {
                netcdfFileWriter.addVariableAttribute(v, attribute);
            }

            // 使用create()来创建该文件，只有create之后才能在文件夹中显示该文件，并且写入数据
            netcdfFileWriter.create();
            //创建x和y方向上的变量。D1    代表一维
            ArrayDouble timevalues = new ArrayDouble.D1(shape[0]);
            ArrayDouble latvalues = new ArrayDouble.D1(shape[1]);
            ArrayDouble lonvalues = new ArrayDouble.D1(shape[2]);

            //创建网格上的变量，D2代表二维，用(width, height)来描述他的定义域，这个顺序必须和ArrayList<Dimension> 加载的顺序一一对应，否则会出错
            ArrayShort values = new ArrayShort.D3(shape[0], shape[1], shape[2]);
            for (int i = 0; i < shape[0]; i++) {
                timevalues.setDouble(i, time_data[i]);
            }
            for (int i = 0; i < shape[1]; i++) {
                latvalues.setDouble(i, lat_data[i]);
            }
            for (int i = 0; i < shape[2]; i++) {
                lonvalues.setDouble(i, lon_data[i]);
            }
            Index index = values.getIndex();
            for (int i = 0; i < shape[0]; i++) {
                for (int j = 0; j < shape[1]; j++) {
                    for (int k = 0; k < shape[2]; k++) {

                        float st = dataVar_data[i][j][k];
                        values.setFloat(index.set(i, j, k), st);
                    }
                }
            }

            netcdfFileWriter.write(vtime, timevalues);
            netcdfFileWriter.write(vlat, latvalues);
            netcdfFileWriter.write(vlon, lonvalues);
            netcdfFileWriter.write(v, values);
            System.out.printf("文件生成完成：%s\n", newFileName);
            new File(filename + ".gbx9").delete();
            new File(filename + ".ncx3").delete();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (netcdfFileWriter != null) {
                    netcdfFileWriter.close();
                }
                if (dataFile != null) {
                    dataFile.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * 遍历文件夹并处理相关的文件
     *
     * @param srcDir
     * @param dstDir
     * @param element
     */
    public void traversingFolders(String srcDir, String dstDir, String element, int fileLength, String oldSuff, String newSuff) {
        File srcFile = new File(srcDir);
        File[] files = srcFile.listFiles();
        for (File temFile : files) {
            if (temFile.isDirectory()) {
                traversingFolders(temFile.toString(), dstDir, element, fileLength, oldSuff, newSuff);
            } else if (temFile.isFile()) {
                // Y:\NAFP\NCEP\GFS\0p25    V:\cloud_cover_forecast\model\NCEP\surface\TCC    Total_cloud_cover_convective_cloud
                // String newFileName = dstDir + "\\" + temFile.getName().substring(14, 22) + temFile.getName().substring(36, 38) + "\\" + temFile.getName().replace("bin", "nc");
                if (temFile.getName().length() == fileLength) {
                    // 文件名的拼接
                    String dirName = dstDir + "\\" + temFile.getName().substring(8, 18);
                    File newDir = new File(dirName);
                    if (!newDir.exists()) {
                        newDir.mkdirs();
                    }
                    String newFileName = dirName + "\\" + temFile.getName().replace(oldSuff, newSuff);
                    copyGrib2OrNc(temFile.toString(), newFileName, element);
                }

            }
        }


    }
}
