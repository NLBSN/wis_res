package com.wis.znwgzh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayShort;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        String encoding = "utf-8";
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        String filePath = null;

        try {
            read = new InputStreamReader(new FileInputStream("newtimeFilePath"), encoding);
            bufferedReader = new BufferedReader(read);
            String line = null;

            if ((line = bufferedReader.readLine()) != null) {
                filePath = line.trim();
            }
        } catch (Exception e) {
            log.error(e);
        }
        //String filePath = "GDFS_NMC_AMEL_QPF_R24_ACHN_LNO_G005_20190212080024024.GRB2";
        if (null == filePath || "".equals(filePath)) {
            log.error("无文件!");
            System.out.println("无文件!");
            return;
        }

        File f = new File("grb2TargetFilePath");
        String[] names = f.list();
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(filePath)) {
                    return;
                }
            }
        }

        NetcdfFile netcdfFile = null;
        Variable LatLon_Projection = null;
        int LatLon_Projection_data = 0;


        Variable lat = null;
        float[] lat_data = (float[]) null;

        Variable lon = null;
        float[] lon_data = (float[]) null;

        Variable reftime = null;
        double[] reftime_data = null;

        Variable time = null;
        double[] time_data = (double[]) null;

        Variable time_bounds = null;
        double[] time_bounds_data = null;

        Variable Total_precipitation_surface_24_Hour_Accumulation = null;
        float[][][] Total_precipitation_surface_24_Hour_Accumulation_data = null;
        try {
            netcdfFile = NetcdfFile.open("grb2FilePath" + filePath);
			/*LatLon_Projection = netcdfFile.findVariable("LatLon_Projection");
			LatLon_Projection_data = (int) LatLon_Projection.read().copyToNDJavaArray();*/

            lat = netcdfFile.findVariable("lat");
            lat_data = (float[]) lat.read().copyToNDJavaArray();

            lon = netcdfFile.findVariable("lon");
            lon_data = (float[]) lon.read().copyToNDJavaArray();

			/*reftime = netcdfFile.findVariable("reftime");
			reftime_data = (double[]) reftime.read().copyToNDJavaArray();*/

            time = netcdfFile.findVariable("time");
            time_data = (double[]) time.read().copyToNDJavaArray();

			/*time_bounds = netcdfFile.findVariable("time_bounds");
			time_bounds_data = (double[]) time_bounds.read().copyToNDJavaArray();*/

            Total_precipitation_surface_24_Hour_Accumulation = netcdfFile.findVariable("Total_precipitation_surface_24_Hour_Accumulation");
            Total_precipitation_surface_24_Hour_Accumulation_data = (float[][][]) Total_precipitation_surface_24_Hour_Accumulation.read().copyToNDJavaArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error(e);
            return;
        }

        NetcdfFileWriter fileWriter;
        try {
            int time_l = time_data.length;
            int lat_l = lat_data.length;
            int lon_l = lon_data.length;
            fileWriter = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, "grb2TargetFilePath" + filePath);
            //分别创建经度和纬度方向的维度，lon的宽度为width,lat高度为height,并命名为lon和lat
            Dimension tdim = fileWriter.addDimension(null, "time", time_l);
            Dimension latdim = fileWriter.addDimension(null, "lat", lat_l);
            Dimension londim = fileWriter.addDimension(null, "lon", lon_l);
            //通过一维组合成二维array list
            ArrayList<Dimension> dims = new ArrayList<Dimension>();
            //注意这里add的顺序很重要,一定要和数据对应，如果把list顺序调换，那么二维的维度也会不同，相当于数学中的坐标系，第一个是x坐标，第二个是y坐标，x宽度为width,y高度为height
            dims.add(tdim);
            dims.add(latdim);
            dims.add(londim);

            //time
            /*Variable vLatLon_Projection=fileWriter.addVariable(null, "LatLon_Projection", DataType.INT, "LatLon_Projection");
            List<Attribute> li_vLatLon_Projection = LatLon_Projection.getAttributes();
            for (Attribute attribute : li_vLatLon_Projection) {
            	fileWriter.addVariableAttribute(vLatLon_Projection, attribute);
			}*/

            Variable vlat = fileWriter.addVariable(null, "lat", DataType.FLOAT, "lat");
            List<Attribute> li_vlat = lat.getAttributes();
            for (Attribute attribute : li_vlat) {
                fileWriter.addVariableAttribute(vlat, attribute);
            }

            Variable vlon = fileWriter.addVariable(null, "lon", DataType.FLOAT, "lon");
            List<Attribute> li_vlon = lon.getAttributes();
            for (Attribute attribute : li_vlon) {
                fileWriter.addVariableAttribute(vlon, attribute);
            }

            Variable vtime = fileWriter.addVariable(null, "time", DataType.DOUBLE, "time");
            List<Attribute> li_vtime = time.getAttributes();
            for (Attribute attribute : li_vtime) {
                fileWriter.addVariableAttribute(vtime, attribute);
            }

            /*Variable vreftime=fileWriter.addVariable(null, "reftime", DataType.DOUBLE, "reftime");
            List<Attribute> li_vreftime = reftime.getAttributes();
            for (Attribute attribute : li_vreftime) {
            	fileWriter.addVariableAttribute(vreftime, attribute);
			}*/

            /*Variable vtime_bounds=fileWriter.addVariable(null, "time_bounds", DataType.DOUBLE, "time_bounds");
            List<Attribute> li_vtime_bounds = time_bounds.getAttributes();
            for (Attribute attribute : li_vtime_bounds) {
            	fileWriter.addVariableAttribute(vtime_bounds, attribute);
			}*/

            //创建变量名称为cloudjudge的变量，对应的维度为dims,该dims为上面定义的一个ArrayList<Diminsion>，该list包含2个维度，经度和维度
            Variable v = fileWriter.addVariable(null, "Total_precipitation_surface_24_Hour_Accumulation", DataType.FLOAT, dims);
            List<Attribute> li_v = Total_precipitation_surface_24_Hour_Accumulation.getAttributes();
            for (Attribute attribute : li_v) {
                //new Attribute("missing_value",-1 )
                if (attribute.getName().equals("missing_value")) {
                    attribute = new Attribute("missing_value", 0);
                }
                fileWriter.addVariableAttribute(v, attribute);
            }
            //fileWriter.addVariableAttribute(v, new Attribute("missing_value", -9999));
            //使用create()来创建该文件，只有create之后才能在文件夹中显示该文件，并且写入数据

            fileWriter.create();
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

                        float st = Total_precipitation_surface_24_Hour_Accumulation_data[i][j][k];
                        values.setFloat(index.set(i, j, k), st);
                    }
                }
            }

            fileWriter.write(vtime, timevalues);
            fileWriter.write(vlat, latvalues);
            fileWriter.write(vlon, lonvalues);
            fileWriter.write(v, values);
            fileWriter.close();
            File file = new File("grb2TargetFilePath" + filePath);
            if (file.exists()) {
                Runtime.getRuntime().exec("chmod 777 -R " + "grb2TargetFilePath" + filePath);
            }
            return;
        } catch (IOException e) {
            log.error(e);
        } catch (InvalidRangeException e) {
            log.error(e);
        }
    }
}
