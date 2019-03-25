package cma.cimiss.demo.test;

import java.util.HashMap;

import cma.cimiss.RequestInfo;
import cma.cimiss.client.DataQueryClient;

public class StaElemStore_storeArray2D_Index {

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
		// 2.3 写入气候环流指数资料代码
		params.put("DataCode", "SEVP_OCEN_GLB_INDEX");
		// 2.4 写入要素：观测时间(yyyymmddhh24miss),年，月，指数1，指数2...指数26
		params.put("Elements",
				"Datetime,Year,Mon,SSTA_NINO12,SSTA_NINO3,SSTA_NINO4,SSTA_NINO34,SSTA_NINOW,SSTA_NINOC,SSTA_NINOA,SSTA_NINOB,SSTA_NINOZ,SSTI_TNA,SSTI_TSA,WHWP,IOWPA,IOWPS,WPWPA,WPWPS,AMONUS,ST_OYA,ST_WWD,ST_KUR,ENSO_MOD,NWP,NCT,IOBW,TIDO,SIDO");

		
		/*195101        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000       14.831       14.256       14.784       11.462       15.334       18.406       15.577     -999.000       13.665       15.577       16.574     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000        0.000       19.876       19.876       19.341       18.317       75.383     5656.318     5829.679     5997.545     3736.338    20656.387      140.000       80.000     4894.871       10.414        2.187        9.930        3.010      146.500    25778.871     2061.933     2528.829       60.403        6.535      -17.694        0.111       -0.429       -0.192        0.053       -0.451        0.273       -0.174        0.663       -0.210     -999.000       -2.800       -0.359       19.534        3.020       12.726       11.444       17.000        5.000        9.000
		195102        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000       14.651       12.919       12.919       14.753       14.434     -999.000     -999.000     -999.000       14.493     -999.000       15.097     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000        0.000       20.259       19.931       19.024       19.936       77.068     6546.644     5842.554     6096.289     3836.397    21731.389      272.500       67.500     4958.464        9.495        5.268       10.690        4.591      150.000    26000.393     2058.903     2478.023       80.758        8.905      -23.157        0.319       -0.625       -0.314       -0.209       -0.505       -0.206       -0.111        0.446        0.480     -999.000       -1.984       -0.365       16.200        2.770       11.730       10.910       10.000        0.000       18.000
		195103        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000        0.000       13.841       11.330       12.915       13.110       15.498     -999.000       15.713       11.442       14.430       13.898       16.094     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000     -999.000        0.000       18.001       18.962       19.455       17.424       72.008     4777.473     3789.105     3499.470     3574.420    15225.668       20.000       72.500     5114.839       11.143        2.961       10.809        3.390      137.500    26157.420     2191.185     2744.278      100.141       -7.668      -18.292       -0.841       -0.064       -0.876        0.154        0.470        0.015     -999.000       -0.885       -0.268     -999.000       -0.932        0.912       11.074       -0.333        9.401        9.361       27.000        0.000        4.000
		*/
		// 写入数据要素值,1951年1-3月的海温指数数据
		String inArray2D[][] = new String[][] {
					{"19510101000000","1951","01","-0.586","-0.784","-0.908","-1.184","0.184","-0.875","-0.298","-0.728","-0.812","-0.089","-1.185","0.000","1.031","0.109","18.723","18.171","0.024","1.169","1.063","-0.168","-0.330","-0.434","-0.821","-0.891","-0.009","1.439"},
					{"19510201000000","1951","02","-0.898","-0.710","-0.856","-1.025","0.173","-0.696","-0.082","-0.698","-0.790","-0.304","-1.055","0.000","4.528","0.770","17.881","16.506","-0.106","1.288","1.131","0.036","-0.331","-0.445","-0.683","-0.889","0.299","0.842"},
					{"19510301000000","1951","03","-0.977","-0.390","-0.339","-0.596","0.085","-0.480","-0.005","-0.585","-0.435","-0.220","-0.890","-0.195","14.562","6.285","18.053","14.844","-0.110","1.255","1.164","0.088","0.186","-0.100","-0.461","-0.761","0.328","1.412"}
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
		params.put("DataCode", "SEVP_OCEN_GLB_INDEX");
		// 2.4 更新要素：指数1
		params.put("valueEles","SSTA_NINO12");
		// 2.5 更新要素条件要素
		params.put("KeyEles", "Datetime");

		// 更新数据要素值，先key要素值，后更新值，更新1951年1与2月的指数1的值
		String inArray2D[][] = new String[][] {
				{ "19510101000000", "0.12" },
				{ "19510201000000", "0.22" }
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
		params.put("DataCode", "SEVP_OCEN_GLB_INDEX");
		// 2.4 更新或写入要素：26个指数
		params.put("valueEles",
				"Datetime,Year,Mon,SSTA_NINO12,SSTA_NINO3,SSTA_NINO4,SSTA_NINO34,SSTA_NINOW,SSTA_NINOC,SSTA_NINOA,SSTA_NINOB,SSTA_NINOZ,SSTI_TNA,SSTI_TSA,WHWP,IOWPA,IOWPS,WPWPA,WPWPS,AMONUS,ST_OYA,ST_WWD,ST_KUR,ENSO_MOD,NWP,NCT,IOBW,TIDO,SIDO");

		// 2.5 更新要素条件要素
		params.put("KeyEles", "Datetime");

		/*更新3月的指数1 【-0.977改为-0.577】，插入4-9月的指数记录*/
		String inArray2D[][] = new String[][] {
				{"19510301000000","1951","03","-0.977","-0.390","-0.339","-0.596","0.085","-0.480","-0.005","-0.585","-0.435","-0.220","-0.890","-0.195","14.562","6.285","18.053","14.844","-0.110","1.255","1.164","0.088","0.186","-0.100","-0.461","-0.761","0.328","1.412"},
				
				{"19510904000000","1951","04","-0.314","-0.291","-0.099","-0.535","0.011","-0.345","-0.140","-0.827","-0.216","0.122","-0.800","-2.138","17.131","12.951","17.979","15.803","0.049","0.566","0.540","-0.098","0.222","0.115","-0.495","-0.805","-0.015","0.931"},
				{"19510905000000","1951","05","0.291","-0.210","0.143","-0.238","-0.031","-0.280","-0.476","-0.550","-0.011","0.076","-0.655","-0.680","16.804","17.586","18.053","17.216","0.120","0.380","0.485","-0.429","0.376","0.143","-0.238","-0.622","0.066","0.655"},
				{"19510906000000","1951","06","1.530","-0.043","-0.139","-0.231","-0.315","-0.145","-0.082","-0.630","0.095","-0.023","-0.726","-1.238","12.830","9.615","19.181","16.228","0.093","1.246","0.590","-0.052","-0.189","-0.047","-0.175","-0.622","-0.012","0.883"},
				{"19510907000000","1951","07","1.788","0.714","-0.008","0.406","-0.326","0.291","-0.186","-0.709","0.542","0.043","-0.542","1.079","7.185","4.145","22.284","19.475","0.240","-0.654","-0.272","-0.213","-0.132","-0.008","0.406","-0.653","-0.219","0.687"},
				{"19510908000000","1951","08","1.225","0.498","-0.197","0.401","-0.553","0.262","0.189","-0.561","0.298","-0.071","-0.266","0.006","5.512","3.355","22.849","16.741","0.028","0.869","0.704","0.159","-0.197","-0.197","0.401","-0.637","-0.183","0.341"},
				{"19510909000000","1951","09","0.545","0.524","-0.113","0.616","-0.302","0.243","0.094","-0.324","0.269","-0.177","-0.140","-0.456","8.003","5.418","24.077","23.728","-0.052","0.702","-0.265","0.109","-0.128","-0.113","0.616","-0.513","0.110","0.437"}
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
		params.put("DataCode", "SEVP_OCEN_GLB_INDEX");

		// 2.4 删除条件要素
		params.put("KeyEles", "Datetime");

		//要素值信息，删除1951年1月的指数记录
		String inArray2D[][] = new String[][] { 
				{ "19510101000000"}
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
