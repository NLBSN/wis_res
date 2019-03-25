package cma.cimiss.demo.rest.DeleteStationDataAPI;

import cma.cimiss.demo.util.RestUtil;

public class DeleteStationDataAPI_REST_rest {
	
/**
 * 利用REST进行站点、指数资料删除
 * URL: http://10.20.76.55/cimiss-web/write?params&instring=xxxx
 * @param args
 */
	public static void main(String[] args) {
		/* 1. 调用方法的参数定义，并赋值 */
		String params = "userId=user_nordb" //1.1用户信息：API账号名
				+ "&pwd=user_nordb_pwd1"    //1.1用户信息： 密码
				+ "&interfaceId=deleteStationData" //1.2 接口ID
				+ "&dataCode=SEVP_WEFC_ACPP_STORE" //1.3 必选参数，资料代码 
				+ "&keyEles=Datetime,Station_Id_C" //1.3 必选参数， 条件要素
	;
		/* 2. 输入要素值 */
		// 不同要素之间用逗号（,）分开，不同记录之间用分号（;）分开
		String instring = "20150114060000,54323" + ";"
				+ "20150114060000,54326";
		/* 3. 调用REST 接口 */
		RestUtil restUtil = new RestUtil();
		String rstData = restUtil.setRestData(params, instring);
		/*返回存储状态信息*/
		System.out.println(rstData);
	}

}
