package cma.cimiss.demo.rest.UpdateStationDataAPI;

import cma.cimiss.demo.util.RestUtil;
import cma.cimiss.demo.util.WebsUtil;

public class UpdateStationDataAPI_REST_rest {
	/**
	 * 利用REST进行站点、指数资料数据更新
	 * URL: http://10.20.76.55/cimiss-web/write?params&instring=xxxx
	 * @param args
	 */
	public static void main(String[] args) {
		/* 1. 调用方法的参数定义，并赋值 */
		String params = "userId=user_nordb" //1.1用户信息：API账号名
				+ "&pwd=user_nordb_pwd1"    //1.1用户信息： 密码
				+ "&interfaceId=updateStationData" //1.2 接口ID
				+ "&dataCode=SEVP_WEFC_ACPP_STORE" //1.3 必选参数，资料代码  
				+ "&keyEles=Datetime,Station_Id_C,Validtime" // 1.3 必选参数，更新条件要素名称
				+ "&valueEles=TEM_Min_2m" ;//1.3必选参数，更新值要素名称(最低气温)
	;

		/* 2. 输入要素值 */
		// 不同要素之间用逗号（,）分开，不同记录之间用分号（;）分开，keyEles与valueEles在一行，先keyEles的值，后valueEles的值
		String inString = "20150114060000,54324,24,-30.02";
		inString = inString + ";" + "20150114060000,54324,72,-30.02";

		/* 3. 调用接口 */
		RestUtil restUtil = new RestUtil();
		String rstData = restUtil.setRestData(params, inString);
		System.out.println(rstData);
	}

}
