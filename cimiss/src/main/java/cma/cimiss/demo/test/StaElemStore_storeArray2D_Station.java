package cma.cimiss.demo.test;

import java.util.HashMap;

import cma.cimiss.RequestInfo;
import cma.cimiss.client.DataQueryClient;

public class StaElemStore_storeArray2D_Station {

	/* 1. 调用类初始化 */
	DataQueryClient client = new DataQueryClient();

	/* 2. 调用方法的参数定义，并赋值 */
	/* 2.1 用户名&密码 */
	String userId = "user_nordb";
	String pwd = "user_nordb_pwd1";
	/* 2.2 . 接口ID */
	String interfaceId = "saveStaionData";
	/* 接口参数定义 */
	HashMap<String, String> params = new HashMap<String, String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void saveEleData() {

		// 接口id
		interfaceId = "saveStationData";
		params = new HashMap<String, String>();
		// 2.3 写入城市天气预报资料代码
		params.put("DataCode", "SEVP_WEFC_ACPP_STORE");
		// 2.4 写入要素：观测时间(yyyymmddhh24miss),年，月，日，时，预报时效，风向.....
		params.put("Elements",
				"Datetime,Station_Id_C,Year,Mon,Day,Hour,Validtime,WIN_D,WIN_Turn,WINF,WINF_Turn,TEM_Max_2m,TEM_Min_2m");

		// 写入数据要素值,20150114060000 54324与54323的预报值
		String inArray2D[][] = new String[][] {
				{"20150114060000","54324","2015","1","14","6","24","1","1","1","1","-50.0000","-50.0000"},
				{"20150114060000","54324","2015","1","14","6","48","8","8","1","1","-50.0000","-50.0000"},
				{"20150114060000","54324","2015","1","14","6","72","5","5","0","0","3.0000","-50.0000"},
				{"20150114060000","54324","2015","1","14","6","96","4","4","1","1","2.0000","-50.0000"},
				{"20150114060000","54324","2015","1","14","6","120","3","3","1","1","2.0000","-50.0000"},
				{"20150114060000","54324","2015","1","14","6","144","7","7","0","0","5.0000","-50.0000"},
				{"20150114060000","54324","2015","1","14","6","168","7","7","1","1","1.0000","-50.0000"},
				{"20150114060000","54323","2015","1","14","6","24","1","1","1","1","-50.0000","-50.0000"},
				{"20150114060000","54323","2015","1","14","6","48","8","8","1","1","-50.0000","-50.0000"},
				{"20150114060000","54323","2015","1","14","6","72","5","5","0","0","2.0000","-50.0000"},
				{"20150114060000","54323","2015","1","14","6","96","4","4","1","1","2.0000","-50.0000"},
				{"20150114060000","54323","2015","1","14","6","120","3","3","1","1","1.0000","-50.0000"}
				};
	

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			RequestInfo rst = client.callAPI_to_storeArray2D(userId, pwd,
					interfaceId, params, inArray2D);
			// 输出结果
			if (rst != null) { // 正常返回
				System.out.println("return code=" + rst.errorCode);
				System.out.println("return message=" + rst.errorMessage);
			}
		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	private void updateEleData() {

		interfaceId = "updateStationData";
		params = new HashMap<String, String>();
		// 2.3 写入资料代码
		params.put("DataCode", "SEVP_WEFC_ACPP_STORE");
		// 2.4 更新要素：
		params.put("valueEles","TEM_Min_2m");
		// 2.5 更新要素条件要素
		params.put("KeyEles", "Datetime,Station_Id_C,Validtime");

		// 更新数据要素值，先key要素值，后更新值，更新20150114060000,54324站，预报时效为24小时的最低气温值为-30.02
		String inArray2D[][] = new String[][] {
				{ "20150114060000","54324","24","-30.02" },
				{ "20150114060000","54324","72","-30.02" }
				};

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			RequestInfo rst = client.callAPI_to_storeArray2D(userId, pwd,
					interfaceId, params, inArray2D);
			// 输出结果
			if (rst != null) { // 正常返回
				System.out.println("return code=" + rst.errorCode);
				System.out.println("return message=" + rst.errorMessage);
			}
		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	private void saveOrUpdateEleData() {

		interfaceId = "saveOrUpdateStationData";
		params = new HashMap<String, String>();
		// 2.3 写入资料代码
		params.put("DataCode", "SEVP_WEFC_ACPP_STORE");
		// 2.4 更新或写入要素：城市天气预报
		params.put("valueEles",
				"Year,Mon,Day,Hour,Validtime,WIN_D,WIN_Turn,WINF,WINF_Turn,TEM_Max_2m,TEM_Min_2m");

		// 2.5 更新要素条件要素
		params.put("KeyEles", "Datetime,Station_Id_C");

		/*插入一下记录*/
		String inArray2D[][] = new String[][] {
				{"20150114060000","54323","2015","1","14","6","168","7","7","1","1","0.0000","-50.0000"},
				{"20150114060000","54326","2015","1","14","6","24","1","1","1","1","-50.0000","-50.0000"},
				{"20150114060000","54326","2015","1","14","6","48","8","8","1","1","-50.0000","-50.0000"},
				{"20150114060000","54326","2015","1","14","6","72","5","5","0","0","2.0000","-50.0000"},
				{"20150114060000","54326","2015","1","14","6","96","4","4","1","1","2.0000","-50.0000"},
				{"20150114060000","54326","2015","1","14","6","120","3","3","1","1","1.0000","-50.0000"},
				{"20150114060000","54326","2015","1","14","6","144","7","7","0","0","4.0000","-50.0000"}				
				};

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			RequestInfo rst = client.callAPI_to_storeArray2D(userId, pwd,
					interfaceId, params, inArray2D);
			// 输出结果
			if (rst != null) { // 正常返回
				System.out.println("return code=" + rst.errorCode);
				System.out.println("return message=" + rst.errorMessage);
			}
		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	private void deleteEleData() {

		interfaceId = "deleteStationData";
		params = new HashMap<String, String>();
		// 2.3 写入资料代码
		params.put("DataCode", "SEVP_WEFC_ACPP_STORE");

		// 2.4 删除条件要素
		params.put("KeyEles", "Datetime,Station_Id_C");

		//要素值信息，删除20150114060000的,54323的记录
		String inArray2D[][] = new String[][] { 
				{ "20150114060000","54323"}
			};

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2 调用接口
			RequestInfo rst = client.callAPI_to_storeArray2D(userId, pwd,
					interfaceId, params, inArray2D);
			// 输出结果
			if (rst != null) { // 正常返回
				System.out.println("return code=" + rst.errorCode);
				System.out.println("return message=" + rst.errorMessage);
			}
		} catch (Exception e) {
			// 异常输出
			e.printStackTrace();
		} finally {
			// 释放接口服务连接资源
			client.destroyResources();
		}
	}

	

}
