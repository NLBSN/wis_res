package com.wis.znwgzh;

import ucar.ma2.ArrayInt;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.IOException;

public class Simple_xy_rd {

    public static void main(String args[]) throws IOException {

        final int NX = 1441;
        final int NY = 2880;
        // This is the array we will read.
        int[][] dataIn = new int[NX][NY];

        // Open the file. The ReadOnly parameter tells netCDF we want
        // read-only access to the file.
        NetcdfFile dataFile = null;
        // String filename = "simple_xy.nc";
        String filename = "D:/data/ec/NAFP_ECMF_FTM_TCC_LNO_GLB_20190421050000_00000-00300.NC";
        // Open the file.
        try {

            dataFile = NetcdfFile.open(filename, null);
            System.out.println(dataFile.getVariables());
            // System.out.println(dataFile.getDimensions());
            // Retrieve the variable named "data"
            Variable dataVar = dataFile.findVariable("tcc");

            if (dataVar == null) {
                System.out.println("Cant find Variable data");
                return;
            }

            // Read all the values from the "data" variable into memory.
            int[] shape = dataVar.getShape();
            int[] origin = new int[2];

            ArrayInt.D2 dataArray;

            dataArray = (ArrayInt.D2) dataVar.read(origin, shape);

            // Check the values.
            assert shape[0] == NX;
            assert shape[1] == NY;

            for (int j = 0; j < shape[0]; j++) {
                for (int i = 0; i < shape[1]; i++) {
                    dataIn[j][i] = dataArray.get(j, i);
                }
            }

            // The file is closed no matter what by putting inside a try/catch block.
        } catch (IOException | InvalidRangeException e) {
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