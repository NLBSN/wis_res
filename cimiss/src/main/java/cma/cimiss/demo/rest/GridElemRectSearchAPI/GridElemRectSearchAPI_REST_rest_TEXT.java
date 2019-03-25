package cma.cimiss.demo.rest.GridElemRectSearchAPI;

import cma.cimiss.demo.util.FormatUtil;
import cma.cimiss.demo.util.RestUtil;
/*
 * REST调取，格点场要素获取（切块），返回TEXT字符串
 */
public class GridElemRectSearchAPI_REST_rest_TEXT {
	/*
	 * main方法 
	 * 如：按经纬范围、起报时间、预报层次、预报时效检索预报要素场 getNafpEleGridInRectByTimeAndLevelAndValidtime
	 */
	public static void main(String[] args) {
		/* 1. 调用方法的参数定义，并赋值 */
		String params = "userId=user_nordb" /* 1.1 用户名&密码 */
				+ "&pwd=user_nordb_pwd1"
				+ "&interfaceId=getNafpEleGridInRectByTimeAndLevelAndValidtime" /* 1.2 接口ID */
				+ "&dataCode=NAFP_FOR_FTM_HIGH_T639_NEHE" /* 1.3 必选参数（按需加可选参数） */// 资料：T639全球中期天气数值预报产品-高分辨率-东北半球
				+ "&time=20140617000000" // 时间
				+ "&validTime=0"// 预报时效：0
				+ "&minLat=39 &maxLat=42 &minLon=115 &maxLon=117"//  经纬度范围：北京及周边（纬度39-42，经度115-117）效
				+ "&fcstEle=TEM"// 预报要素（单个)：气温
				+ "&fcstLevel=1000"// 预报层次（单个)：1000hpa
				+ "&dataFormat=text"; /* 1.4 序列化格式 */

		/* 2. 调用接口 */
		RestUtil restUtil = new RestUtil();
		String rstData = restUtil.getRestData(params);

		/* 3. 输出结果 */
		FormatUtil formatUtil = new FormatUtil();
		formatUtil.outputRstText(rstData);
	}
}
