/* This is part of the netCDF package.
   Copyright 2006 University Corporation for Atmospheric Research/Unidata.
   See COPYRIGHT file for conditions of use.

   This is a very simple example which writes a 2D array of
   sample data. To handle this in netCDF we create two shared
   dimensions, "x" and "y", and a netCDF variable, called "data".

   This example demonstrates the netCDF Java API.

   Full documentation of the netCDF Java API can be found at:
   http://www.unidata.ucar.edu/software/thredds/current/netcdf-java/documentation.htm
*/

package com.wis.guan;

import ucar.nc2.Dimension;
import ucar.ma2.*;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Simple_xy_wr {

    public static void main(String args[]) {
        // We are writing 2D data, a 6 x 12 grid.
        // 写一个2D数据，一个6x12的网格
        final int NX = 6;
        final int NY = 12;

        String filename = "simple_xy.nc";
        NetcdfFileWriter dataFile = null;

        try {
            dataFile = NetcdfFileWriter.createNew(NetcdfFileWriter.Version.netcdf3, filename);

            // Create netCDF dimensions,创建netCDF的尺寸
            Dimension xDim = dataFile.addDimension(null, "x", NX);
            Dimension yDim = dataFile.addDimension(null, "y", NY);

            // define dimensions  定义尺寸
            List<Dimension> dims = new ArrayList<>();
            dims.add(xDim);
            dims.add(yDim);

            // Define a netCDF variable. The type of the variable in this case is ncInt (32-bit integer).
            //  定义一个netCDF变量，在这种情况下变量的类型是ncInt
            Variable dataVariable = dataFile.addVariable(null, "data", DataType.INT, dims);

            // create the file
            dataFile.create();

            // This is the data array we will write. It will just be filled with a progression of numbers for this example.
            // 这是我们要编写的数据数组。 这个例子只会填充数字的进展。
            ArrayInt.D2 dataOut = new ArrayInt.D2(xDim.getLength(), yDim.getLength());

            // Create some pretend data. If this wasn't an example program, we would have some real data to write, for example, model output.
            // 创建一些假数据。 如果这不是一个示例程序，我们会有一些真实的数据要写，例如模型输出。
            int i, j;

            for (i = 0; i < xDim.getLength(); i++) {
                for (j = 0; j < yDim.getLength(); j++) {
                    dataOut.set(i, j, i * NY + j);
                }
            }

            // Write the pretend data to the file. Although netCDF supports reading and writing subsets of data, in this case we write all the data in one operation.
            // 将假数据写入文件。 虽然netCDF支持读写数据子集，但在这种情况下，我们在一次操作中写入所有数据。
            dataFile.write(dataVariable, dataOut);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidRangeException e) {
            e.printStackTrace();
        } finally {
            if (null != dataFile)
                try {
                    dataFile.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
        }

        System.out.println("*** SUCCESS writing example file " + filename + " !");
    }

}