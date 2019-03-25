package cma.cimiss.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class RestUtil {

	/*
	 * TODO:REST服务地址，依具体环境设置
	 */
	private final String host = "10.20.76.32:8008";
//	private final String host = "10.20.76.33:8008";
	private final int timeoutInMilliSeconds = 1000 * 60 * 2; // 2 MINUTE

	/*
	 * REST请求服务，获取数据
	 */
	public String getRestData(String params) {
		StringBuilder retStr = new StringBuilder();
		URI uri = null;
		URL url = null;
		BufferedReader reader = null;
		URLConnection con;
		try {
			uri = new URI("http", this.host, "/cimiss-web/api", params, "");
			url = uri.toURL();
			con = url.openConnection();
			con.setConnectTimeout(this.timeoutInMilliSeconds);
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
//				System.out.println("读取到的原始数据："+line);
				retStr.append(line).append("\r\n");
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return retStr.toString();
	}

	/*
	 * REST请求服务，存储数据
	 */
	public String setRestData(String params, String inString) {
		StringBuilder retStr = new StringBuilder();
		URI uri = null;
		URL url = null;
		java.io.BufferedReader reader = null;
		URLConnection con;
		params = params + "&instring=" + inString;
		try {
			uri = new URI("http", this.host, "/cimiss-web/write", params, "");
			url = uri.toURL();
			con = url.openConnection();
			con.setConnectTimeout(this.timeoutInMilliSeconds);
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = reader.readLine();
			while (line != null) {
				retStr.append(line).append("\r\n");
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return retStr.toString();
	}

}
