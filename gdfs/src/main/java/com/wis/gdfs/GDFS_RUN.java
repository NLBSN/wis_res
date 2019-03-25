package com.wis.gdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @description 已知bug：一天需要执行两次时，第一次的结果如果还在文件夹，会影响第二次的结果。需要写一个过滤的程序，先将原来的过滤掉。(搞懂业务)
 * @author fan
 *
 */
public class GDFS_RUN {

	// Z_NWGD_C_BABJ_20180326021656_P_RFFC_SCMOC-ER24_201803260800_24024.GRB2
	private static String oneTag = null;// = "SCMOC-ER24";//
	// GDFS_NMC_AMEL_QPF_R24_ACHN_LNO_G005_20180328080024024.GRB2
	private static String twoTag = null;// = "QPF_R24";//
	private static String twoWord1 = null;// = "GDFS_NMC_AMEL";
	private static String twoWord2 = null;// = "ACHN_LNO_G005";
	private static String pathDir = null;// = "D:\\test";
	private static Properties prop = null;
	private static Map<String, String> map = null;

	/**
	 * @description 加载配置文件
	 */
	static {
		InputStream in = null;
		try {
			// 包内读取
			// in = GDFS_RUN.class.getClassLoader().getResourceAsStream("prop.properties");
			in = new FileInputStream("prop.properties");

			prop = new Properties();
			prop.load(in);
			oneTag = prop.getProperty("OLDTAG");
			twoTag = prop.getProperty("NEWTAG");
			twoWord1 = prop.getProperty("NEWTAGWORD1");
			twoWord2 = prop.getProperty("NEWTAGWORD2");
			pathDir = prop.getProperty("PATHDIR");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())+"属性文件加载完成");
		}
	}

	public void run() {
		File file = new File(pathDir);
		map = new HashMap<String, String>();
		if (file.isDirectory()) {
			System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())+"文件夹路径为：" + file.getPath());
			File[] files = file.listFiles();
			// file2
			// D:\test\Z_NWGD_C_BABJ_20180326021896_P_RFFC_SCMOC-ER24_201803260800_24024.GRB2
			for (File file2 : files) {
				System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())+"即将处理的文件为："+file2.getName());
				if (file2.isFile()) {
					// Z_NWGD_C_BABJ_20180326021896_P_RFFC_SCMOC-ER24_201803260800_24024.GRB2
					String fileName = file2.getName();
					// System.out.println(fileName);
					if (fileName.indexOf(oneTag) != -1) {
						// [Z, NWGD, C, BABJ, 20180326021656, P,
						// RFFC,SCMOC-ER24, 201803260800, 24024.GRB2]
						String[] fileNameOS = fileName.split("_");
						// SCMOC-ER2420180326080024024.GRB2
						String fileNameOP = fileNameOS[7] + fileNameOS[8] + fileNameOS[9];
						// 新文件夹名
						// GDFS_NMC_AMEL_QPF_R24_ACHN_LNO_G005_20180326080024024.GRB2
						String fileNameT = twoWord1 + "_" + twoTag + "_" + twoWord2 + "_" + fileNameOS[8]
								+ fileNameOS[9];

						if (!map.containsKey(fileNameOP)) {
							map.put(fileNameOP, fileNameOS[4]);
							new File("" + file2).renameTo(new File(pathDir + "\\" + fileNameT));
							// System.out.println("---------------"+new File(""
							// + file2));
							// System.out.println("---------------"+new
							// File(pathDir + "\\" + fileNameT));
						} else {
							long oldNum = Long.parseLong(map.get(fileNameOP));// 20180326021656
							if (Long.parseLong(fileNameOS[4]) > oldNum) {
								map.put(fileNameOP, fileNameOS[4]);
								// File oldFile = new File(pathDir + "\\" +
								// fileNameT);//D:\test\GDFS_NMC_AMEL_QPF_R24_ACHN_LNO_G005_20180326080024024.GRB2
								// System.out.println(oldFile);
								new File(pathDir + "\\" + fileNameT).delete();
								new File("" + file2).renameTo(new File(pathDir + "\\" + fileNameT));
								System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())+"文件重命名完成");
							} else {
								new File("" + file2).delete();
								System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())+file2.getName()+"文件删除成功");
							}
						}
					} /*
						 * else if(fileName.indexOf(twoTag) != -1){
						 * 
						 * }
						 */
				}else{
					System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())+"这是一个文件夹，忽略此文件夹");
				}
			}
		}
	}

}
