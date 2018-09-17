package com.report.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;

/**
 * @description 将文本信息写入到word文档中
 * @author fan
 *
 */
public class IOTxt {

	/**
	 * @description 解析从url获取到的文本信息，并将文本信息写入到word文档中
	 * @param parseUtlStr
	 *            从url获取到的文本信息
	 * @param filePath
	 *            doc文档的路径
	 * @throws IOException
	 */
	public void IOTotxt(String parseUtlStr, String filePath, int ImgBiLi) throws IOException {
		BufferedReader bufferRead = null;
		OutputStreamWriter outStrWrit = null;

		String fileName = null;
		try {
			bufferRead = new BufferedReader(new StringReader(parseUtlStr));

			String line = null;
			while ((line = bufferRead.readLine()) != null) {
				if (line.contains("中央气象台沿岸海区预报")) {
					fileName = line.replace("中央气象台沿岸海区预报", "").trim();
					String tmp = fileName;
					if (tmp.charAt(tmp.length() - 2) == 56) {
						fileName = tmp.substring(0, 4) + tmp.substring(5, 7) + tmp.substring(8, 10) + "0"
								+ tmp.substring(11, 12);
					} else {
						fileName = tmp.substring(0, 4) + tmp.substring(5, 7) + tmp.substring(8, 10)
								+ tmp.substring(11, 13);
					}
					if (filePath.contains("yyyy")) {
						filePath = filePath.replace("yyyy", fileName);
					}
					File file = new File(filePath);
					System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())
							+ " 文件是否需要创建：" + !file.exists());
					if (!file.exists()) {
						if (!file.getParentFile().exists()) {
							file.getParentFile().mkdirs();
						}
						System.out
								.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())
										+ " 文件是否创建成功:" + file.createNewFile());
					}
					outStrWrit = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
				}
				outStrWrit.write(line.replace(" ", "\t"));
				outStrWrit.write("\n");
				outStrWrit.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outStrWrit.close();
				bufferRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())
					+ " 文件写入完成：" + filePath);

		}
	}
}
