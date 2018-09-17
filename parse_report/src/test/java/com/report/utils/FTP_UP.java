package com.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * @description ftp进行文件的上传
 * @author wis
 *
 */
public class FTP_UP {

	// ftp对象
	private FTPClient ftp;
	// 需要连接到的ftp端的ip
	@SuppressWarnings("unused")
	private String ip;
	// 连接端口，默认21
	@SuppressWarnings("unused")
	private int port;
	// 要连接到的ftp端的名字
	@SuppressWarnings("unused")
	private String name;
	// 要连接到的ftp端的对应得密码
	@SuppressWarnings("unused")
	private String pwd;

	// 调用此方法，输入对应得ip，端口，要连接到的ftp端的名字，要连接到的ftp端的对应得密码。连接到ftp对象，并验证登录进入fto
	public FTP_UP(String ip, int port, String name, String pwd) {
		ftp = new FTPClient();
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.pwd = pwd;

		// 验证登录
		try {
			ftp.connect(ip, port);
			System.out.println(ftp.login(name, pwd));
			ftp.setCharset(Charset.forName("UTF-8"));
			ftp.setControlEncoding("UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// //验证登录
	// public void login() {
	// try {
	// ftp.connect(ip, port);
	// System.out.println(ftp.login(name, pwd));
	// ftp.setCharset(Charset.forName("UTF-8"));
	// ftp.setControlEncoding("UTF-8");
	//
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	// 获取ftp某一文件（路径）下的文件名字,用于查看文件列表
	public void getFilesName() {
		try {
			// 获取ftp里面，“Windows”文件夹里面的文件名字，存入数组中
			FTPFile[] files = ftp.listFiles("/Windows");
			// 打印出ftp里面，“Windows”文件夹里面的文件名字
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 上传文件
	public void putFile() {
		try {
			// 将本地的"D:/1.zip"文件上传到ftp的根目录文件夹下面，并重命名为"aaa.zip"
			System.out.println(ftp.storeFile("fan/apache-storm-0.9.6.tar.gz",
					new FileInputStream(new File("D:/apache-storm-0.9.6.tar.gz"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 上传文件的第二种方法，优化了传输速度
	public void putFile2(String localFile, String remoteFile) {
		try {
			OutputStream os = ftp.storeFileStream(remoteFile);
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(new File(localFile));

			byte[] b = new byte[1024];
			int len = 0;
			while ((len = fis.read(b)) != -1) {
				os.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 下载文件
	public void getFile() {
		try {
			// 将ftp根目录下的"jsoup-1.10.2.jar"文件下载到本地目录文件夹下面，并重命名为"1.jar"
			ftp.retrieveFile("fan/jsoup-1.10.2.jar", new FileOutputStream(new File("D:/1.jar")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 下载文件的第二种方法，优化了传输速度
	public void getFile2() {
		try {
			InputStream is = ftp.retrieveFileStream("03.png");

			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(new File("D:/2.png"));

			byte[] b = new byte[1024];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		FTP_UP m = new FTP_UP("10.0.122.164", 21, "service", "service123");

		m.putFile();
		String localFile = "";
		String remoteFile = "";
		m.putFile2(localFile, remoteFile);
		m.getFile();
		m.getFile2();
	}
}
