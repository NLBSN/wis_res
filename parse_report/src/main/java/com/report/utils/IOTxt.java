package com.report.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.rtf.RtfWriter2;

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
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void IOTotxt(String parseUtlStr, String filePath, int ImgBiLi) throws DocumentException, IOException {
		BufferedReader bufferRead = null;
		Document doc = null;
		Paragraph par = new Paragraph();
		Image img = null;
		// 大标题
		Font font = new Font(Font.BOLD, 14, Font.BOLD, new Color(0, 0, 0));
		// BaseFont bfFont
		// =BaseFont.createFont("SIMSUN",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		// 正文
		Font font1 = new Font(Font.NORMAL, 12, Font.NORMAL, new Color(0, 0, 0));
		// 小标题
		Font font2 = new Font(Font.NORMAL, 12, Font.BOLD, new Color(0, 0, 0));
		if (filePath.contains("yyyy")) {
			String df = new SimpleDateFormat("yyyyMMddHH").format(new Date());
			filePath = filePath.replace("yyyy", df);
		}
		File file = new File(filePath);
		if (file.exists()) {
			boolean flag = file.delete();
			System.out.println(
					new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 文件是否删除：" + flag);
			file.createNewFile();
		} else {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		try {
			bufferRead = new BufferedReader(new StringReader(parseUtlStr));
			doc = new Document(PageSize.A4, 72, 72, 90, 90);
			RtfWriter2.getInstance(doc, new FileOutputStream(new File(filePath)));
			doc.open();
			String aa = "";
			par.setLeading(24);
			int i = 1;
			wc: while ((aa = bufferRead.readLine()) != null) {
				if (aa.length() < 1) {
					continue;
				}
				par.setFont(font1);
				par.setAlignment(Element.ALIGN_LEFT);
				if (aa.contains("tttttttttttt")) {
					par.setAlignment(Element.ALIGN_CENTER);
					par.setFont(font);
					par.add(aa.replace("tttttttttttt", ""));
					doc.add(par);
					par.clear();
					continue;
				} else if (aa.contains("pppppppppp")) {
					par.setAlignment(Element.ALIGN_CENTER);
					par.add(aa.replace("pppppppppp", ""));
					doc.add(par);
					par.clear();
					continue;
				} else if (aa.contains("-------------")) {
					if (i == 1) {
						par.add("\n");
						i++;
					}
					if (aa.contains("影响与关注")) {
						par.setFont(font2);
						par.add("    " + aa.replace("-------------", ""));
						doc.add(par);
						par.clear();
						par.setFont(font1);
						while ((aa = bufferRead.readLine()) != null) {
							if (aa.contains("http")) {
								break wc;
							}
							if (aa.length() < 1) {
								continue;
							}
							par.add("    " + aa);
							doc.add(par);
							par.clear();
						}
					}
					par.setFont(font2);
					par.add("    " + aa.replace("-------------", ""));
					doc.add(par);
					par.clear();
					continue;
				} else if (aa.contains("http")) {
					byte[] data = imageToJpg(aa);
					img = Image.getInstance(data);
					img.setAbsolutePosition(0, 0);
					// img.scaleAbsolute(24, 14);
					img.setAlignment(Image.LEFT);
					// 图片缩放比例
					img.scalePercent(ImgBiLi);
					par.add(img);
					doc.add(par);
					par.clear();
					continue;
				} else {
					par.add("    " + aa);
					doc.add(par);
					par.clear();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				doc.close();
				bufferRead.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis())
					+ " 文件写入完成：" + filePath);

		}
	}

	/**
	 * @description 将从url获取到的文本中的http(图片信息)下载下来，并保存为二进制的形式
	 * @param aa
	 *            http的网址链接
	 * @return 二进制的图片信息
	 * @throws IOException
	 * @throws DocumentException
	 */
	private byte[] imageToJpg(String aa) throws IOException, DocumentException {
		Connection.Response execute = Jsoup.connect(aa + System.currentTimeMillis()).ignoreContentType(true).execute();
		byte[] data = execute.bodyAsBytes();
		return data;
	}

	@SuppressWarnings("unused")
	private byte[] imageToTxt(String aa) throws IOException {
		// new一个URL对象
		URL url = new URL(aa);

		// 打开链接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// 设置请求方式为"GET"
		conn.setRequestMethod("GET");

		// 超时响应时间为5秒
		conn.setConnectTimeout(5 * 1000);

		// 通过输入流获取图片数据
		InputStream inStream = conn.getInputStream();

		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
		byte[] data = readInputStream(inStream);

		// new一个文件对象用来保存图片，默认保存当前工程根目录
		// File imageFile = new File("pic20170419.jpg");
		// // 创建输出流
		// FileOutputStream outStream = new FileOutputStream(imageFile);
		// // 写入数据
		// outStream.write(data);
		// // 关闭输出流
		// outStream.close();

		return data;
	}

	private byte[] readInputStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];

		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;

		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();

		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}
