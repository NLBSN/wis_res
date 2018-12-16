package com.report.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

public class ParseUrl {

    /**
     * @throws IOException
     * @throws DocumentException
     * @description 读取配置文件并做循环处理
     */
    public void cycle() throws IOException, DocumentException {
        int num = Integer.parseInt(Pare_Bean.getNum());
        String parseUtlStr = null;
        for (int i = 1; i <= num; i++) {
            String url = null;
            try {
                url = Pare_Bean.getProp().getProperty("URL_" + i).trim();
                String[] urls = url.split(",");
                parseUtlStr = parseUrl(urls[0]);
                int ImgBiLi = 55;
                if (urls.length >= 3) {
                    ImgBiLi = Integer.parseInt(urls[2]);
                }
                new IOTxt().IOTotxt(parseUtlStr, urls[1], ImgBiLi);
//				System.out.println("----------"+localFile+"++++++++"+new File(localFile).getName());
//				new FTP_UP("10.0.122.164", 21, "service", "service123").putFile2(localFile, "fan/"+new File(localFile).getName());

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (url == null) {
                continue;
            }
        }
        System.out.println(
                new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " " + "所有文件处理完成");
    }

    /**
     * @param url 需要处理的url
     * @return 读取到的文本信息
     * @throws IOException
     * @description 解析url获取到的文本信息
     */
    public String parseUrl(String url) {
        String content = "";
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url).ignoreContentType(true).method(Connection.Method.GET)
                    .headers(new HashMap<String, String>() {
                        /**
                         *
                         */
                        private static final long serialVersionUID = -8566080312795794267L;

                        {
                            put("Host", "www.nmc.cn");
                            put("Connection", "keep-alive");
                            put("Upgrade-Insecure-Requests", "1");
                            put("User-Agent",
                                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
                            put("Accept",
                                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                            put("Accept-Encoding", "gzip, deflate");
                            put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7");
                        }
                    }).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String body = response.body();

            org.jsoup.nodes.Document parse = Jsoup.parse(body);

            Element text = parse.getElementById("text");

            Elements select = text.select(".author");

            content += select.text() + "\n";

            // select = text.select(".author");
            // content += select.text() + "\n";

            select = text.select(".writing");
            // content+=select.text()+"\n";

            Element element = select.get(0);
            Elements children = element.children();

            for (Element ele : children) {
                if (ele.is("div")) {
                    if (ele.toString().contains("subhead")) {
                        content += ele.text() + "tttttttttttt" + "\n";
                    } else {
                        content += ele.text() + "\n";
                        Elements img = ele.select("img");
                        if (img != null) {
                            for (Element el : img) {
                                content += el.attr("src") + "\n";
                            }
                        }
                    }
                } else if (ele.select("p").toString().length() > 1) {
                    // 判断是否是标题
                    if (ele.select("b").toString().length() > 1) {
                        content += ele.text() + "-------------" + "\n";
                    } else if (ele.select("p").toString().contains("text-align")) {
                        content += ele.text() + "pppppppppp" + "\n";
                    } else {
                        content += ele.text() + "\n";
                    }
                } else if (ele.select("table").toString().length() > 1) {
                    int num = ele.select("tr").size();
                    for (int i = 0; i < num; i++) {
                        ele = select.select("tr").get(i);
                        content += ele.text() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//		System.out.println(content);
        return content;
    }

}
