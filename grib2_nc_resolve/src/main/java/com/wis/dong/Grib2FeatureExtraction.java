package com.wis.dong;

/**
 * @Description: wis_res     要素提取
 * @author: fan
 * @Date: Created in 2019/4/21 11:06
 * @Modified By:
 */
public class Grib2FeatureExtraction {

    public static void main(String args[]) {
        String srcDir = "Y:/NAFP/CMA/GRAPES_MESO";
        String dstDir = "V:\\cloud_cover_forecast\\model\\GRAPES-MESO\\surface\\TCC";
        // Y:\NAFP\NCEP\GFS\0p25    V:\cloud_cover_forecast\model\NCEP\surface\TCC    Total_cloud_cover_convective_cloud
        // Total_cloud_cover_surface
        // Y:/NAFP/CMA/GRAPES_MESO/20190901/12/rmf.gra.2019090112073.grb2    V:\cloud_cover_forecast\model\GRAPES-MESO\surface\TCC    Total_cloud_cover_cloud_base
        String element = "Total_cloud_cover_cloud_base";
        int fileLength = 26;
        String oldSuff = "grb2";
        String newSuff = "nc";
        new Utils().traversingFolders(srcDir, dstDir, element, fileLength, oldSuff, newSuff);
    }


}