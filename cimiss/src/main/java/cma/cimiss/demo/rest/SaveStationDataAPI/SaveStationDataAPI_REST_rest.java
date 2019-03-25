package cma.cimiss.demo.rest.SaveStationDataAPI;

import cma.cimiss.demo.util.RestUtil;
import cma.cimiss.demo.util.WebsUtil;

public class SaveStationDataAPI_REST_rest {
	/**
	 * 利用REST进行站点、指数资料数据保存
	 * URL: http://10.20.76.55/cimiss-web/write?params&instring=xxxx
	 * @param args
	 */
	public static void main(String[] args) {
		/* 1. 调用方法的参数定义，并赋值 */
		String params = "userId=user_nordb" //1.1用户信息：API账号名
				+ "&pwd=user_nordb_pwd1"    //1.1用户信息： 密码
				+ "&interfaceId=saveStationData" //1.2 接口ID
				+ "&dataCode=SEVP_WEFC_ACPP_STORE" //1.3 必选参数，资料代码 
				+ "&elements=Datetime,Station_Id_C,Year,Mon,Day,Hour,Validtime,WIN_D,WIN_Turn,WINF,WINF_Turn,TEM_Max_2m,TEM_Min_2m" // 1.3 必选参数，要素名称：时间，站号、年、月、日、时、预报时效、风向......
	;

		/* 2. 输入要素值 */
		// 不同要素之间用逗号（,）分开，不同记录之间用分号（;）分开
		String instring = "20150114060000,54326,2015,1,14,6,72,5,5,0,0,2.0000,-50.0000;"
				+ "20150114060000,54326,2015,1,14,6,96,4,4,1,1,2.0000,-50.0000";
		/* 3. 调用REST 接口 */
	    RestUtil restUtil = new RestUtil() ;
	    String rstData = restUtil.setRestData( params,instring ) ;  
	    System.out.println(rstData);
	}

}
