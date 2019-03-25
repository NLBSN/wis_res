package cma.cimiss.demo.rest.FileInfoSearchAPI;

import cma.cimiss.demo.util.FormatUtil;
import cma.cimiss.demo.util.RestUtil;
/*
 * REST调取，文件信息列表检索，返回JSONP格式
 * URL: http://10.20.76.55/cimiss-web/api?params
 */
public class FileInfoSearchAPI_REST_rest_JSONP {
	/*
	   * main方法
	   * 如：按时间段、站号检索雷达文件 getRadaFileByTimeRangeAndStaId
	   */
	  public static void main(String[] args) {
	    /* 1. 调用方法的参数定义，并赋值 */
	    String params ="userId=user_nordb" /* 1.1 用户名&密码 */
	        + "&pwd=user_nordb_pwd1"
	        + "&interfaceId=getRadaFileByTimeRangeAndStaId" /* 1.2 接口ID */        
	        + "&dataCode=RADA_L2_FMT" /* 1.3 必选参数（按需加可选参数） */ //资料：质控前标准格式单站多普勒雷达基数据
	        + "&timeRange=[20140809123000,20140809123600)" //时间段，前闭后开
	        + "&staIds=Z9859,Z9852,Z9856,Z9851,Z9855" //雷达站
	        +"&callbackName=apiData"+ "&dataFormat=jsonp"; /* 1.4 序列化格式 */  
	       
	    /* 2. 调用接口 */
	    RestUtil restUtil = new RestUtil() ;
	    String rstData = restUtil.getRestData( params ) ;

	    /* 3.  输出结果 */
	    FormatUtil formatUtil = new FormatUtil() ;
	    formatUtil.outputRstText( rstData ) ;
	  }
}
