package com.example.log.http;

import java.util.Date;

public class DownloadTask {
    //哪一个时间段的日志 yyyyMMdd HH 以小时为范围
    private Date fileDate;
    //项目名称
    private String applicationName;
    //处理结果
    private boolean result;
    //
    private Long costTime;

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }
}
