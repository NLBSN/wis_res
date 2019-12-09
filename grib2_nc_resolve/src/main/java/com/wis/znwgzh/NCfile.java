package com.wis.znwgzh;

import ucar.ma2.ArrayFloat;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @description 使用java来写入nc文件
 */
public class NCfile {
    public static int width = 20, height = 16;
    public static double lonWest = 110, latSouth = 30;
    public static double gridInterval = 0.5;

    public static void main(String args[]) {
        double d[][] = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                d[i][j] = Math.sin(i + j);
            }
        }
        //放到根目录会创建失败
        String filepath = "d://output//testNCfile.nc";
        NCfile.createNCfile(filepath, width, height, lonWest, latSouth, gridInterval, d);
    }

    /**
     * @param filepath     创建的文件路径
     * @param width        网格横向长度
     * @param height       网格纵向高度
     * @param lonWest      网格左下角起始经度
     * @param latSouth     网格左下角起始维度
     * @param gridInterval 网格间距
     * @param d            d[i][j]为网格i和j处对应的数据
     * @throws IOException
     * @throws InvalidRangeException
     */
    public static void createNCfile(String filepath, int width, int height, double lonWest, double latSouth, double gridInterval, double d[][]) {
        //创建netcdf3写入文件对象
        NetcdfFileWriter fileWriter;
        try {
            fileWriter = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, filepath);
            //分别创建经度和纬度方向的维度，lon的宽度为width,lat高度为height,并命名为lon和lat
            Dimension xdim = fileWriter.addDimension(null, "lon", width);
            Dimension ydim = fileWriter.addDimension(null, "lat", height);
            //通过一维组合成二维array list
            ArrayList<Dimension> dims = new ArrayList<Dimension>();
            //注意这里add的顺序很重要,一定要和数据对应，如果把list顺序调换，那么二维的维度也会不同，相当于数学中的坐标系，第一个是x坐标，第二个是y坐标，x宽度为width,y高度为height
            dims.add(xdim);
            dims.add(ydim);
            //创建名称为lon的变量,类型为folat，对应的维度为lon,对应Dimension里面定义的名称为"lon"的那个
            Variable vx = fileWriter.addVariable(null, "lon", DataType.FLOAT, "lon");
            //给vx变量加上数据说明，全程long_name
            fileWriter.addVariableAttribute(vx, new Attribute("long_name", "longitude"));
            //给vx变量加上units，对应的Degree_east，如果不加units为Degree_east(东经)，则不是地理坐标系，这个很重要
            fileWriter.addVariableAttribute(vx, new Attribute("units", "Degrees_east"));

            Variable vy = fileWriter.addVariable(null, "lat", DataType.FLOAT, "lat");
            fileWriter.addVariableAttribute(vy, new Attribute("long_name", "latitude"));
            //给vy变量加上units，对应的Degree_north，如果不加units为Degree_north(北纬)，则不是地理坐标系，这个很重要
            fileWriter.addVariableAttribute(vy, new Attribute("units", "Degrees_north"));

            //创建变量名称为var的变量，对应的维度为dims,该dims为上面定义的一个ArrayList<Diminsion>，该list包含2个维度，经度和维度
            Variable v = fileWriter.addVariable(null, "var", DataType.FLOAT, dims);
            fileWriter.addVariableAttribute(v, new Attribute("long_name", "数据的详细描述"));
            fileWriter.addVariableAttribute(v, new Attribute("units", "unit"));
            //使用create()来创建该文件，只有create之后才能在文件夹中显示该文件，并且写入数据
            fileWriter.create();
            //创建x和y方向上的变量。D1    代表一维
            ArrayFloat xvalues = new ArrayFloat.D1(width);
            ArrayFloat yvalues = new ArrayFloat.D1(height);
            //创建网格上的变量，D2代表二维，用(width, height)来描述他的定义域，这个顺序必须和ArrayList<Dimension> 加载的顺序一一对应，否则会出错
            ArrayFloat values = new ArrayFloat.D2(width, height);
            Index index = values.getIndex();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    //将二维数组[i][j]处的数据写入网格的i,j处
                    values.setDouble(index.set(i, j), d[i][j]);
                }
            }
            for (int i = 0; i < width; i++) {
                xvalues.setFloat(i, (float) (lonWest + gridInterval * i));
            }
            for (int j = 0; j < height; j++) {
                yvalues.setFloat(j, (float) (latSouth + gridInterval * j));
            }
            fileWriter.write(vx, xvalues);
            fileWriter.write(vy, yvalues);
            fileWriter.write(v, values);
            fileWriter.close();
            System.out.println("文件已存入" + filepath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("文件创建失败，请检查路径");
        } catch (InvalidRangeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("文件写入错误，超出范围");
        }

    }
}