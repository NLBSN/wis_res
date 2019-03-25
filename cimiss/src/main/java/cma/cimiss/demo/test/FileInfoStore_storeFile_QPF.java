package cma.cimiss.demo.test;


import java.util.HashMap;

import cma.cimiss.RequestInfo;
import cma.cimiss.client.DataQueryClient;

public class FileInfoStore_storeFile_QPF {

	/* 1.接口调用客户端类 */
	DataQueryClient client = new DataQueryClient();
	/* 2.1 用户名、密码 */
	String userId = "user_nordb";
	String pwd = "user_nordb_pwd1";
	/* 2.2 接口id */
	String interfaceId = "";

	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// 保存文件
		FileInfoStore_storeFile_QPF fs = new FileInfoStore_storeFile_QPF();

		// 保存文件接口示例
		fs.saveFileData();
		// 更新文件接口示例
//		fs.UpdateFile();
		// 删除文件接口示例
//		fs.deleteFileData();
		// 保存或更新接口示例
//		fs.saveOrUpdateFile();

	}

	// 保存文件测试
	private void saveFileData() {
		// 接口ID赋值
		interfaceId = "saveFiles";
		// 接口参数信息
		HashMap<String, String> params = new HashMap<String, String>();
		// 2.3写入资料代码
		params.put("DataCode", "SEVP_MICAPS_QPF");
		// 2.4写入文件属性要素信息字段定义
		params.put("Elements",
				"Datetime,FILE_NAME,FORMAT,FILE_SIZE,Validtime");

		// 写入文件属性要素数据值
		String inArray2D[][] = new String[][] {
				{ "20181210000000",  "MOP_CHCC_NCEPRA_WMFS_TMP_NH_0_PM_20181110_0000.png","PNG", "1222","12" },
				// { "rdb_dp_d_suat.c", "10", ".c", "20150924010000","22222" },
				{ "20181211000000","MOP_CHCC_NCEPRA_WMFS_TMP_NH_0_PM_20181110_0000.png", "PNG", "1222" ,"24"} };

		int num=inArray2D.length;
		// 写入文件对应路径（本地）
		String[] write_filenames = new String[num];
		String path = "./test";

		
		for(int i=0;i<num;i++)
		{
			write_filenames[i] = path + "/" + inArray2D[i][1];
		}

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2调用写入资料方法
			RequestInfo r = client.callAPI_to_storeFile(userId, pwd,
					interfaceId, params, inArray2D, write_filenames);
			if (r != null) {
				System.out.println("return code=" + r.errorCode);
				System.out.println("return message=" + r.errorMessage);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			client.destroyResources();
		}
	}

	private void deleteFileData() {
		// 接口ID赋值
		interfaceId = "deleteFiles";
		// 参数信息
		HashMap<String, String> params = new HashMap<String, String>();
		// 2.3资料名称
		params.put("DataCode", "SEVP_MICAPS_QPF");

		// 2.4更新文件属性条件关键要素
		params.put("KeyEles", "Datetime");

		// 保存或更新文件属性要素值
		String inArray2D[][] = new String[][] { { "20150924010000" },
				{ "20150924010000" }, { "20150924010000"} };

		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2调用写入资料方法
			RequestInfo r = client.callAPI_to_storeFile(userId, pwd,
					interfaceId, params, inArray2D, null);
			if (r != null) {
				System.out.println("return code=" + r.errorCode);
				System.out.println("return message=" + r.errorMessage);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			client.destroyResources();
		}
	}

	// 保存文件测试
	private void saveOrUpdateFile() {
		// 接口ID赋值
		interfaceId = "saveOrUpdateFiles";
		// 接口参数信息
				HashMap<String, String> params = new HashMap<String, String>();
				// 2.3写入资料代码
				params.put("DataCode", "SEVP_MICAPS_QPF");
				
				// 2.4更新文件属性条件关键要素
				params.put("KeyEles", "Datetime");
				
				// 2.5保存或更新文件属性要素信息字段定义
				params.put("valueEles",
						"FILE_NAME,FORMAT,FILE_SIZE,Validtime");
				
				// 写入文件属性要素数据值
				String inArray2D[][] = new String[][] {
						{ "20151110000000",  "MOP_CHCC_NCEPRA_WMFS_TMP_NH_0_PM_20151110_0000.png","PNG", "1222","12" },
						// { "rdb_dp_d_suat.c", "10", ".c", "20150924010000","22222" },
						{ "20151111000000","MOP_CHCC_NCEPRA_WMFS_TMP_NH_0_PM_20151110_0000.png", "PNG", "1222" ,"24"} };

				int num=inArray2D.length;
				// 写入文件对应路径（本地）
				String[] write_filenames = new String[num];
				String path = "./CIPAS产品";

				
				for(int i=0;i<num;i++)
				{
					write_filenames[i] = path + "/" + inArray2D[i][1];
				}


		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2调用写入资料方法
			RequestInfo r = client.callAPI_to_storeFile(userId, pwd,
					interfaceId, params, inArray2D, write_filenames);
			if (r != null) {
				System.out.println("return code=" + r.errorCode);
				System.out.println("return message=" + r.errorMessage);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			client.destroyResources();
		}
	}

	// 更新文件测试
	private void UpdateFile() {
		// 接口ID赋值
		interfaceId = "updateFiles";
		// 参数信息
		HashMap<String, String> params = new HashMap<String, String>();
		// 2.3 写入资料名称
		params.put("DataCode", "SEVP_MICAPS_QPF");
		// 2.4保存或更新文件属性要素信息字段定义，更新发布时间与区域
		params.put("valueEles",
				"Validtime");
		// 2.5更新文件属性条件关键要素
		params.put("KeyEles", "Datetime");

		// 保存或更新文件属性要素值
		String inArray2D[][] = new String[][] {
				{ "20151111000000",  "6" }
		};


		int num=inArray2D.length;
		// 保存或更新文件对应路径（本地）
		String[] write_filenames = new String[num];
		String path = "./CIPAS产品";

		
		for(int i=0;i<num;i++)
		{
			write_filenames[i] = path + "/" + "MOP_CHCC_NCEPRA_WMFS_TMP_NH_0_PM_20151111_0000.png";
		}
				
		/* 3. 调用接口 */
		try {
			// 3.1 初始化接口服务连接资源
			client.initResources();
			// 3.2调用写入资料方法
			RequestInfo r = client.callAPI_to_storeFile(userId, pwd,
					interfaceId, params, inArray2D, write_filenames);
			if (r != null) {
				System.out.println("return code=" + r.errorCode);
				System.out.println("return message=" + r.errorMessage);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			client.destroyResources();
		}
	}

}
