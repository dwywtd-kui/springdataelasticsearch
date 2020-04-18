package com.inspur.springdataelasticsearch;

import com.inspur.springdataelasticsearch.tika.TikaFileUtil;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.jpeg.JpegParser;
import org.apache.tika.sax.BodyContentHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TikaTest {

    @Test
    public void testDocx(){
        try {
            String s = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\测试附件文档.docx");
            System.out.println(s);
            String s1 = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\浪潮集团邮箱账号密码修改方法V3 0.docx");
            System.out.println(s1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPdf() throws IOException, TikaException {
        String s = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\测试附件文档.pdf");
        System.out.println(s);
    }

    @Test
    public void testxlsx() throws IOException, TikaException {
        String s = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\3-问题记录累计汇总20200319-20200417.xlsx");
        System.out.println(s);
    }

    @Test
    public void testPpt() throws IOException, TikaException {
        String s = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\企业门户功能.pptx");
        System.out.println(s);
    }

    @Test
    public void testTxt() throws IOException, TikaException {
        String s = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\test.txt");
        System.out.println(s);

    }

    @Test
    public void testJpg() throws TikaException, SAXException, IOException {
        String s = TikaFileUtil.doc2String("C:\\Users\\hanliukui\\Desktop\\Test1.jpg");
        System.out.println("内容"+s);
    }
}
