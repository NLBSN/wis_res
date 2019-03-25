package cma.cimiss.demo.rest.SaveOrUpdateStationDataAPI;

import cma.cimiss.demo.util.RestUtil;
import cma.cimiss.demo.util.WebsUtil;

public class SaveOrUpdateStationDataAPI_REST_rest {
	/**
	 * 利用REST进行站点、指数资料数据保存或更新,如果存在则更新 ，不存在则保存
	 * URL: http://10.20.76.55/cimiss-web/write?params&instring=xxxx
	 * @param args
	 */
	public static void main(String[] args) {
		/* 1. 调用方法的参数定义，并赋值 */
		String params = "userId=user_nordb" //1.1用户信息：API账号名
				+ "&pwd=user_nordb_pwd1"    //1.1用户信息： 密码
				+ "&interfaceId=saveOrUpdateStationData" //1.2 接口ID
				+ "&dataCode=SEVP_WEFC_ACPP_STORE" //1.3 必选参数，资料代码  
				+ "&keyEles=Datetime,Station_Id_C" //1.3 必选参数，更新条件要素
				+ "&valueEles=Year,Mon,Day,Hour,Validtime,WIN_D,WIN_Turn,WINF,WINF_Turn,TEM_Max_2m,TEM_Min_2m" ;//1.3 必选参数, 更新值要素名         
	;

		/* 2. 输入要素值 */
		// 保存或更新要素值，如果存在则更新 ，不存在则保存
		// 字段之间用逗号（,）分隔，行之间用分号(;)分开,前面的为key要素的值，后面为value要素的值
		String instring = "20150114060000,54323,2015,1,14,6,168,7,7,1,1,0.0000,-50.0000";
		instring = instring
				+ ";"
				+ "20150114060000,54326,2015,1,14,6,24,1,1,1,1,-50.0000,-50.0000";
		instring = instring
				+ ";"
				+ "20150114060000,54326,2015,1,14,6,48,8,8,1,1,-50.0000,-50.0000";

		/* 3. 调用REST接口 */
		RestUtil restUtil = new RestUtil();
		String rstData = restUtil.setRestData(params, instring);
		System.out.println(rstData);
	}

}
