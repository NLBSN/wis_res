package cma.cimiss.demo.test;

import java.util.HashMap;

import cma.cimiss.RequestInfo;
import cma.cimiss.client.DataQueryClient;

public class StaElemStoreAPI_CLIB_callAPI_to_storeSerializedStr {

	/* 1. 调用类初始化 */
	DataQueryClient client = new DataQueryClient();

	/* 2. 调用方法的参数定义，并赋值 */
	/* 2.1 用户名&密码 */
	String userId = "user_nordb";
	String pwd = "user_nordb_pwd1";
	/* 2.2 . 接口ID */
	String interfaceId = "saveStationData";
	/* 接口参数定义 */
	String params = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void saveEleSerializedStr() {
		/* 2.2 . 接口ID */
		interfaceId = "saveStationData";
		// 接口ID
		// 2.3 资料代码
		params = "DataCode=SEVP_WEFC_ACPP_STORE";
		// 2.4 写入要素：时间，站号、年、月、日、时、预报时效、风向......
		params =params+ "&"
				+ "Elements=Datetime,Station_Id_C,Year,Mon,Day,Hour,Validtime,WIN_D,WIN_Turn,WINF,WINF_Turn,TEM_Max_2m,TEM_Min_2m";

		// 写入要素序列化数据值，字段之间用逗号（,）分隔，行之间用分号(;)分开,前面的为key要素的值，后面为value要素的值
		String inArrayString = "20150114060000,54326,2015,1,14,6,72,5,5,0,0,2.0000,-50.0000;"
				+ "20150114060000,54326,2015,1,14,6,96,4,4,1,1,2.0000,-50.0000";
		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			int rst = client.callAPI_to_storeSerializedStr(userId, pwd,
					interfaceId, params, inArrayString);
			// 输出结果
			System.out.println("return code=" + rst);

		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	private void updateEleSerializedStr() {

		//接口ID
		/* 2.2 . 接口ID */
		interfaceId = "updateStationData";

		// 2.3 资料代码
		params = "DataCode=SEVP_WEFC_ACPP_STORE";
		// 2.4 更新要素字段：最低气温
		params = params + "&"
				+ "valueEles=TEM_Min_2m";
		// 2.5 更新条件要素字段
		params = params + "&" + "keyEles=Datetime,Station_Id_C,Validtime";

		// 序列化数据，字段之间用逗号（,）分隔，行之间用分号(;)分开,前面的为key要素的值，后面为value要素的值
		String inArrayString = "20150114060000,54324,24,-30.02";
		inArrayString = inArrayString + ";"
				+ "20150114060000,54324,72,-30.02";

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			int rst = client.callAPI_to_storeSerializedStr(userId, pwd,
					interfaceId, params, inArrayString);
			// 输出结果
			System.out.println("return code=" + rst);
		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	private void saveOrUpdateEleSerializedStr() {
		/* 2.2 . 接口ID */
		String interfaceId = "saveOrUpdateStationData";
		params = "";
		// 2.3 资料代码
		params = "DataCode=SEVP_WEFC_ACPP_STORE";
		// 2.4 更新要素字段：年、月、日、时、预报时效，风向......
		params = params + "&"
				+ "valueEles=Year,Mon,Day,Hour,Validtime,WIN_D,WIN_Turn,WINF,WINF_Turn,TEM_Max_2m,TEM_Min_2m";
		// 2.5 更新条件要素字段
		params = params + "&" + "keyEles=Datetime,Station_Id_C";

		// 字段之间用逗号（,）分隔，行之间用分号(;)分开,前面的为key要素的值，后面为value要素的值
		String inArrayString = "20150114060000,54323,2015,1,14,6,168,7,7,1,1,0.0000,-50.0000";
		inArrayString = inArrayString + ";"
				+ "20150114060000,54326,2015,1,14,6,24,1,1,1,1,-50.0000,-50.0000";
		inArrayString = inArrayString + ";"
				+ "20150114060000,54326,2015,1,14,6,48,8,8,1,1,-50.0000,-50.0000";
		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			int rst = client.callAPI_to_storeSerializedStr(userId, pwd,
					interfaceId, params, inArrayString);
			// 输出结果
			System.out.println("return code=" + rst);

		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	private void deleteEleSerializedStr() {
		//接口ID
		interfaceId = "deleteStationData";
		// 2.3 资料代码
		params = "DataCode=SEVP_WEFC_ACPP_STORE";
		// 2.4 删除记录条件要素名称
		params = params + "&" + "keyEles=Datetime,Station_Id_C";

		// 条件要素值
		String inArrayString = "20150114060000,54323";
		inArrayString = inArrayString + ";" + "20150114060000,54326";

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			int rst = client.callAPI_to_storeSerializedStr(userId, pwd,
					interfaceId, params, inArrayString);
			// 输出结果
			System.out.println("return code=" + rst);

		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

}
