package cma.cimiss.demo.rest.GridElemPointSearchAPI;

import cma.cimiss.demo.util.FormatUtil;
import cma.cimiss.demo.util.RestUtil;
/*
 * REST调取，返回JSON格式
 */
public class GridElemPointSearchAPI_REST_rest_JSON {
	/*
	 * main方法 
	 * 如：按起报时间、预报层次、预报时段、经纬度检索预报要素插值 getNafpEleAtPointByTimeAndLevelAndValidtimeRange
	 */
	public static void main(String[] args) {
		/* 1. 调用方法的参数定义，并赋值 */
		String params = "userId=user_nordb" /* 1.1 用户名&密码 */
				+ "&pwd=user_nordb_pwd1"
				+ "&interfaceId=getNafpEleAtPointByTimeAndLevelAndValidtimeRange" /* 1.2 接口ID */
				+ "&dataCode=NAFP_FOR_FTM_HIGH_T639_NEHE" /* 1.3 必选参数（按需加可选参数） */// 资料：T639全球中期天气数值预报产品-高分辨率-东北半球
				+ "&time=20140716000000" // 时间
				+ "&minVT=0"// 起始预报时效
				+ "&maxVT=240"// 终止预报时效
				+ "&latLons=39.8/116.4667,31.2/121.4333"// 经纬度点，北京（纬度39.8，经度116.4667）、上海（纬度31.2，经度121.4333）
				+ "&fcstEle=TEM"// 预报要素（单个)：气温
				+ "&fcstLevel=1000"// 预报层次（单个)：1000hpa
				+ "&dataFormat=json"; /* 1.4 序列化格式 */

		/* 2. 调用接口 */
		RestUtil restUtil = new RestUtil();
		String rstData = restUtil.getRestData(params);

		/* 3. 输出结果 */
		FormatUtil formatUtil = new FormatUtil();
		formatUtil.outputRstJson(rstData);
	}
}
