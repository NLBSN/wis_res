package cma.cimiss.demo.rest.StaElemSearchAPI;

import cma.cimiss.demo.util.FormatUtil;
import cma.cimiss.demo.util.RestUtil;
import cma.cimiss.demo.util.WebsUtil;

public class StaElemSearchAPI_REST_rest_KML {

	/*
	   * main方法
	   * 如：按时间检索地面数据要素 getSurfEleByTime
	   */
	  public static void main(String[] args) {
	    /* 1. 调用方法的参数定义，并赋值 */
	    String params ="userId=user_nordb" /* 1.1 用户名&密码 */
	        + "&pwd=user_nordb_pwd1"
	        + "&interfaceId=getSurfEleByTime" /* 1.2 接口ID */        
	        + "&dataCode=SURF_CHN_MUL_HOR" /* 1.3 必选参数（按需加可选参数） */ //资料：中国地面逐小时
	        + "&elements=lat,lon,PRE_1h" //检索要素：纬度、经度、小时降水
	        + "&times=20140617000000" //检索时间
	       // + "&ctLevel=-10,5,10,50,100" //色标分级
	        + "&eleValueRanges=PRE_1h:(,100)" //检索要素值范围
	        + "&limitCnt=10"  //返回最多记录数：10
	        + "&dataFormat=kml-p" ; /* 1.4 序列化格式 */
	

	    /* 2. 调用接口 */
	    RestUtil restUtil = new RestUtil() ;
	    String rstData = restUtil.getRestData( params ) ;
	    
	    /* 3.  输出结果 */
	    FormatUtil formatUtil = new FormatUtil() ;
	    formatUtil.outputRstText( rstData ) ;
	  }

}
