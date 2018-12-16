package com.report.utils;

import java.awt.Color;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.sun.deploy.util.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * @author fan
 * @description 将文本信息写入到word文档中
 */
public class IOTxt {


    public void analys(StringBuffer stringBuffer) {
        String[] split = stringBuffer.toString().split("\n", -1);
        try {
            for (String tmp : split) {
                String[] tmpLies = tmp.split(",", -1);
                if (tmpLies[0].equals("diyizhong")) {
                    // 解析url  diyizhong,中石油天气公报,http://www.nmc.cn/publish/weather-bulletin/index.htm,D:/fenfa_zidongdua/zsy/tqgb/zsy_tqgb_yyyymmdd.txt
                    String parseUtlStr = new ParseUrl().parseUrl(tmpLies[2]);
                    // System.out.println(parseUtlStr);
                    for (int i = 3; i < tmpLies.length; i++) {
                        // System.out.println("------" + tmpLies[i]);
                        if (tmpLies[i].contains("txt")) {
                            IOToText(parseUtlStr, tmpLies[i]);
                        }
                        if (tmpLies[i].contains("jpg")) {
                            IOToImage(parseUtlStr, tmpLies[i]);
                        }
                        if (tmpLies[i].contains("doc")) {
                            IOTotxt(parseUtlStr, tmpLies[i], 55);
                        }
                    }
                } else if (tmpLies[0].equals("dierzhong")) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * @param parseUtlStr 从url获取到的文本信息
     * @param filePath    doc文档的路径
     * @throws DocumentException
     * @throws IOException
     * @description 解析从url获取到的文本信息，并将文本信息写入到word文档中
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

        try {
            bufferRead = new BufferedReader(new StringReader(parseUtlStr));
            String hs = "";
            while ((hs = bufferRead.readLine().trim()) != null) {
                if (hs.length() < 1) {
                    continue;
                }

                // 中国气象局中央气象台 10月25日18时02分
                // 制作：陈博宇 陈双 签发：何立富 2018 年 10 月 25 日 18 时

                hs = hs.replaceAll(" ", "");
                filePath = filePath.split("yyyymm")[0];
                if (hs.contains("年")) {
                    String[] nianSplit = hs.split("年");
                    String[] yueSplit = nianSplit[1].split("月");
                    String[] riSplit = yueSplit[1].split("日");
                    if (riSplit.length > 1) {
                        String[] shiSplit = riSplit[1].split("时");
                        if (shiSplit.length > 1) {
                            String[] fenSplit = shiSplit[1].split("分");
                            filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + shiSplit[0] + fenSplit[0] + ".doc";
                        } else {
                            filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + shiSplit[0] + ".doc";
                        }
                    } else {
                        filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + ".doc";
                    }
                } else if (hs.contains("月")) {
                    String[] yueSplit = hs.split("月");
                    String[] riSplit = yueSplit[1].split("日");
                    String[] shiSplit = riSplit[1].split("时");

                    if (shiSplit[1].length() > 1) {
                        String[] fenSplit = shiSplit[1].split("分");
                        try {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 2) + riSplit[0] + shiSplit[0] + fenSplit[0] + ".doc";
                        } catch (Exception e) {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 1) + riSplit[0] + shiSplit[0] + fenSplit[0] + ".doc";
                        }
                    } else {
                        try {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 2) + riSplit[0] + shiSplit[0] + ".doc";
                        } catch (Exception e) {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 1) + riSplit[0] + shiSplit[0] + ".doc";
                        }
                    }
                }
                File file = new File(filePath);
                if (file.exists()) {
                    System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 文档已经存在：" + filePath);
                    return;//TODO 这儿要做修改
                } else {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    // file.createNewFile();
                }
                break;
            }

            doc = new Document(PageSize.A4, 72, 72, 90, 90);
            RtfWriter2.getInstance(doc, new FileOutputStream(new File(filePath)));
            doc.open();
            String aa = "";
            par.setLeading(24);
            int i = 1;
            wc:
            while ((aa = bufferRead.readLine()) != null) {
                if (aa.length() < 1 || aa.contains("预报：") || aa.contains("预报:") || aa.contains("制作：") || aa.contains("制作:")) {
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
            System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 文档写入完成：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null)
                    doc.close();
                if (bufferRead != null)
                    bufferRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    /**
     * @param http http的网址链接
     * @return 二进制的图片信息
     * @throws IOException
     * @throws DocumentException
     * @description 将从url获取到的文本中的http(图片信息)下载下来，并保存为二进制的形式
     */
    private byte[] imageToJpg(String http) throws IOException, DocumentException {
        Connection.Response execute = Jsoup.connect(http + System.currentTimeMillis()).ignoreContentType(true).execute();
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


    private boolean IOToImage(String parseUtlStr, String filePath) {
        BufferedReader bufferRead = null;
        Image img = null;
        FileOutputStream fileOutputStream = null;
        try {
            bufferRead = new BufferedReader(new StringReader(parseUtlStr));
            String hs = "";
            while ((hs = bufferRead.readLine().trim()) != null) {
                if (hs.length() < 1) {
                    continue;
                }
                // 中国气象局中央气象台 10月25日18时02分
                // 制作：陈博宇 陈双 签发：何立富 2018 年 10 月 25 日 18 时
                hs = hs.replaceAll(" ", "");
                filePath = filePath.split("yyyymm")[0];
                if (hs.contains("年")) {
                    String[] nianSplit = hs.split("年");
                    String[] yueSplit = nianSplit[1].split("月");
                    String[] riSplit = yueSplit[1].split("日");
                    if (riSplit.length > 1) {
                        String[] shiSplit = riSplit[1].split("时");
                        if (shiSplit.length > 1) {
                            String[] fenSplit = shiSplit[1].split("分");
                            filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + shiSplit[0] + fenSplit[0] + ".jpg";
                        } else {
                            filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + shiSplit[0] + ".jpg";
                        }
                    } else {
                        filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + ".jpg";
                    }
                } else if (hs.contains("月")) {
                    String[] yueSplit = hs.split("月");
                    String[] riSplit = yueSplit[1].split("日");
                    String[] shiSplit = riSplit[1].split("时");

                    if (shiSplit.length > 1) {
                        String[] fenSplit = shiSplit[1].split("分");
                        try {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 2) + riSplit[0] + shiSplit[0] + fenSplit[0] + ".jpg";
                        } catch (Exception e) {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 1) + riSplit[0] + shiSplit[0] + fenSplit[0] + ".jpg";
                        }
                    } else {
                        try {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 2) + riSplit[0] + shiSplit[0] + ".jpg";
                        } catch (Exception e) {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 1) + riSplit[0] + shiSplit[0] + ".jpg";
                        }
                    }
                }
                File file = new File(filePath);
                if (file.exists()) {
                    System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 图片已经存在：" + filePath);
                    return false;//TODO 这儿要做修改
                } else {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    // file.createNewFile();
                }
                break;
            }


            String aa = "";
            int i = 1;
            wc:
            while ((aa = bufferRead.readLine()) != null) {
                if (aa.length() < 1) {
                    continue;
                }
                if (aa.contains("-------------")) {
                    if (aa.contains("影响与关注")) {
                        while ((aa = bufferRead.readLine()) != null) {
                            if (aa.contains("http")) {
                                break wc;
                            }
                            if (aa.length() < 1) {
                                continue;
                            }
                        }
                    }
                    continue;
                } else if (aa.contains("http")) {
                    fileOutputStream = new FileOutputStream(new File(filePath));
                    byte[] data = imageToTxt(aa);
                    // byte[] data = imageToJpg(aa);
                    fileOutputStream.write(data);
                    fileOutputStream.flush();
                    System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 图片生成完成：" + filePath);
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (bufferRead != null)
                    bufferRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }


    private boolean IOToText(String parseUtlStr, String filePath) {
        BufferedReader bufferRead = null;
        BufferedWriter bufferedWriter = null;
        try {
            bufferRead = new BufferedReader(new StringReader(parseUtlStr));
            String hs = "";
            while ((hs = bufferRead.readLine()) != null) {
                if (hs.trim().length() < 1) {
                    continue;
                }
                // 中国气象局中央气象台 10月25日18时02分
                // 制作：陈博宇 陈双 签发：何立富 2018 年 10 月 25 日 18 时
                hs = hs.replaceAll(" ", "");
                filePath = filePath.split("yyyymm")[0];
                if (hs.contains("年")) {
                    String[] nianSplit = hs.split("年");
                    String[] yueSplit = nianSplit[1].split("月");
                    String[] riSplit = yueSplit[1].split("日");
                    if (riSplit.length > 1) {
                        String[] shiSplit = riSplit[1].split("时");
                        if (shiSplit.length > 1) {
                            String[] fenSplit = shiSplit[1].split("分");
                            filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + shiSplit[0] + fenSplit[0] + ".txt";
                        } else {
                            filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + shiSplit[0] + ".txt";
                        }
                    } else {
                        filePath = filePath + nianSplit[0].substring(nianSplit[0].length() - 4) + yueSplit[0] + riSplit[0] + ".txt";
                    }

                } else if (hs.contains("月")) {
                    String[] yueSplit = hs.split("月");
                    String[] riSplit = yueSplit[1].split("日");
                    String[] shiSplit = riSplit[1].split("时");

                    if (shiSplit.length > 1) {
                        String[] fenSplit = shiSplit[1].split("分");
                        try {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 2) + riSplit[0] + shiSplit[0] + fenSplit[0] + ".txt";
                        } catch (Exception e) {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 1) + riSplit[0] + shiSplit[0] + fenSplit[0] + ".txt";
                        }
                    } else {
                        try {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 2) + riSplit[0] + shiSplit[0] + ".txt";
                        } catch (Exception e) {
                            filePath = filePath + Calendar.getInstance().get(Calendar.YEAR) + yueSplit[0].substring(yueSplit[0].length() - 1) + riSplit[0] + shiSplit[0] + ".txt";
                        }
                    }
                }
                File file = new File(filePath);
                if (file.exists()) {
                    System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 文件已经存在：" + filePath);
                    return false;//TODO 这儿要做修改
                } else {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    // file.createNewFile();
                }
                break;
            }

            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "utf-8"));

            String aa = "";
            int i = 1;
            wc:
            while ((aa = bufferRead.readLine()) != null) {
                if (aa.length() < 1 || aa.contains("http") || aa.contains("pppppppppp") || aa.contains("预报：") || aa.contains("预报:") || aa.contains("制作：") || aa.contains("制作:")) {
                    continue;
                }
                bufferedWriter.write(aa.replaceAll("tttttttttttt", "").replaceAll("-------------", ""));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(System.currentTimeMillis()) + " 文件写入完成：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (bufferRead != null)
                    bufferRead.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;

        }
    }
}
