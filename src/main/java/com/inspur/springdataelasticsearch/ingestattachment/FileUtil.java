package com.inspur.springdataelasticsearch.ingestattachment;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/4
 */
public class FileUtil {

    //从远程服务器读取文件
    public static String pdfToBase64(String url) throws IOException {
        InputStream is = null;
        URL url2 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
        conn.setDoInput(true);
        conn.connect();
        is = conn.getInputStream();

        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new BASE64Encoder().encode(data).replace("\n", "").replace("\r", "");
    }


    //将文件转为base64
    public static String docToBase64(String path) throws IOException {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer).replace("\n", "").replace("\r", "");
    }

    //将文件转为base64
    public static String docToBase64(File file) throws IOException {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer).replace("\n", "").replace("\r", "");
    }

}
