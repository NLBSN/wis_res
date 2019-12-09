package com.wis.dong;

/**
 * @Description: wis_res     要素提取
 * @author: fan
 * @Date: Created in 2019/4/21 11:06
 * @Modified By:
 */
public class Grib2FeatureExtraction {

    public static void main(String args[]) {
        String srcDir = "Y:\\NAFP\\NCEP\\GFS\\0p25";
        String dstDir = "V:\\cloud_cover_forecast\\model\\NCEP\\surface\\TCC";
        Utils utils = new Utils();
        utils.traversingFolders(srcDir,dstDir);
        //
    }


}