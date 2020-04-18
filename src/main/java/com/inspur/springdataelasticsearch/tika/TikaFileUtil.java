package com.inspur.springdataelasticsearch.tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

/**
 * @Desc
 * @Created By liukuihan
 * @date on 2020/4/18
 */
public class TikaFileUtil {

    public static String doc2String(String filePath) throws IOException, TikaException {
        Tika tika = new Tika();
        String s = tika.parseToString(new File(filePath));
        return s;
    }
}
