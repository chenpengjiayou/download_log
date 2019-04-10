package com.example.log;

import com.example.log.http.DownloadTask;
import com.example.log.http.GzipUtil;
import com.example.log.http.HttpDownload;
import com.example.log.task.SaticScheduleTask;
import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadLogApplicationTests {
    private static String[] applicationNames = new String[]{"basicservice-server", "bos-server", "cms-server", "das-server", "dds-server", "fms-server", "irs-server", "oms-server", "pms-server", "pws-server", "scm-server", "scs-server", "ses-server", "spcms-server", "tms-server", "tomcat-b2cnew", "tomcat-bos", "tomcat-cms", "tomcat-fms", "tomcat-kdb", "tomcat-mcs", "tomcat-ocs", "tomcat-oms", "tomcat-pcs", "tomcat-pms", "tomcat-pws", "tomcat-scm", "tomcat-scs", "tomcat-spcms", "tomcat-tms", "tomcat-tp", "tomcat-ts", "tomcat-uc", "tomcat-uias", "tomcat-wms", "tomcat-wos", "tomcat-xxl", "ts-server", "uc-server", "wms-server", "wos-server"};
    @Test
    public void contextLoads() {
        String filepath = "D:\\cp\\temp\\log\\all\\";
        //HttpDownload.download(url, filepath);
        Date date = DateUtils.parseDate("2019-04-02 23:00:00",new String[]{"yyyy-MM-dd HH:mm:ss"});
        List<DownloadTask> failureList = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for(int i=0;i<applicationNames.length;i++) {
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.setApplicationName(applicationNames[i]);
            downloadTask.setFileDate(date);
            String ymd = DateUtils.formatDate(date,"yyyy/MM/dd");
            String hour = DateUtils.formatDate(date,"HH");
            String ymdh = DateUtils.formatDate(date,"yyyyMMddHH");
            String file ="";
            if(applicationNames[i].endsWith("server")) {
                file = String.format("stdout-%s.log.gz",hour);
            } else if(applicationNames[i].startsWith("tomcat")) {
                file = String.format("catalina-%s.out.gz",hour);
            }
            String url = String.format("http://39.108.148.225:8080/%s/%s/%s", ymd, applicationNames[i],file);
            System.out.println(url);
            String fileName = String.format("%s-%s.log.gz",applicationNames[i],ymdh);
            boolean result = HttpDownload.download(url, filepath+fileName);
            downloadTask.setResult(result);
            if(!result) {
                failureList.add(downloadTask);
                System.out.println("download failure");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("本批任务耗时："+(end-begin)/1000);
        System.out.println("失败任务数："+failureList.size());

        File file = new File("D:\\cp\\temp\\log\\all\\");
        File[] files = file.listFiles();
        for(int i=0;i<files.length;i++) {
            GzipUtil.unGzipFile(files[i].getAbsolutePath());
            files[i].delete();
        }
    }
    @Test
    public void gzipTest() {
        File file = new File("D:\\cp\\temp\\log\\all\\");
        File[] files = file.listFiles();
        for(int i=0;i<files.length;i++) {
            GzipUtil.unGzipFile(files[i].getAbsolutePath());
            files[i].delete();
        }
    }
}
