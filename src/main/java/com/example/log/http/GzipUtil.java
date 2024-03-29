package com.example.log.http;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

public class GzipUtil {
    public static void unGzipFile(String sourcedir) {
        String ouputfile = "";
        try {
            //建立gzip压缩文件输入流
            FileInputStream fin = new FileInputStream(sourcedir);
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);
            //建立解压文件输出流
            ouputfile = sourcedir.substring(0,sourcedir.lastIndexOf('.'));
            FileOutputStream fout = new FileOutputStream(ouputfile);

            int num;
            byte[] buf=new byte[1024];

            while ((num = gzin.read(buf,0,buf.length)) != -1)
            {
                fout.write(buf,0,num);
            }

            gzin.close();
            fout.close();
            fin.close();
        } catch (Exception ex){
            System.err.println(sourcedir+" "+ex.toString());
        }
    }
}
