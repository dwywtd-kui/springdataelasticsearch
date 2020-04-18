package com.inspur.springdataelasticsearch.filetest.util;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Base64Util {

    public static String file2Base64(File file){
        byte[] buffer=null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.encode(buffer);
    }

    public static String str2Base64(String str){
        return Base64.encode(str.getBytes());
    }
}

