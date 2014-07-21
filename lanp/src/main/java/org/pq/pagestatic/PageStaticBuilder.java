package org.pq.pagestatic;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pq.pagestatic.base.HttpClientCompleteListener;
import org.pq.pagestatic.base.RsyncCompleteListener;
import org.pq.pagestatic.bean.RsyncDir;
import org.pq.pagestatic.bean.RsyncRemote;
import org.pq.pagestatic.impl.PageStaticSpecParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageStaticBuilder {
    private Logger logger = LoggerFactory.getLogger(PageStatic.class);
    private List<RsyncRemote> rsyncRemotes = new ArrayList<RsyncRemote>();
    private List<RsyncDir> rsyncDirs = new ArrayList<RsyncDir>();
    private RsyncCompleteListener rsyncCompleteListener;
    private HttpClientCompleteListener httpClientCompleteListener;

    private int uploadTriggerMaxFiles = 100;
    private int uploadTriggerMaxSeconds = 120;
    private boolean deleteLocalDirAfterRsync = true;
    private int maxGeneratingThreads = 1000;
    private int httpSocketTimeoutSeconds = 30;
    private int rsyncTimeoutSeconds = 30;
    private String rsyncOptions = "-az";
    private String tempDir;
    private String proxyHost;
    private int proxyPort;
    private int rsyncRetryTimes = 3;

    public PageStatic build() {
        validateConfig();
        return new PageStatic(this);
    }

    //得到配置信息
    public PageStaticBuilder fromSpec(String specName) {
        new PageStaticSpecParser(specName).parse(this);
        return this;
    }

    //增加rsync需要同步到的远程主机：主机ip,主机用户
    public PageStaticBuilder addRsyncRemote(String romoteHost, String remoteUser) {
        rsyncRemotes.add(new RsyncRemote(romoteHost, remoteUser));
        return this;
    }

    //增加rsync同步到 的文件夹
    public PageStaticBuilder addRsyncDir(String localDir, String remoteDir) {
        rsyncDirs.add(new RsyncDir(localDir, remoteDir));
        return this;
    }

    //当文件达到多少文件数量时才触发上传
    public PageStaticBuilder triggerUploadWhenMaxFiles(int uploadTriggerMaxFiles) {
        if (uploadTriggerMaxFiles > 0)
            this.uploadTriggerMaxFiles = uploadTriggerMaxFiles;
        return this;
    }

    //最多隔多少秒 触发上传
    public PageStaticBuilder triggerUploadWhenMaxSeconds(int uploadTriggerMaxSeconds) {
        if (uploadTriggerMaxSeconds > 0)
            this.uploadTriggerMaxSeconds = uploadTriggerMaxSeconds;
        return this;
    }

    //是否在同步后删除本地的文件夹
    public PageStaticBuilder deleteLocalDirAfterRsync(boolean deleteLocalDirAfterRsync) {
        this.deleteLocalDirAfterRsync = deleteLocalDirAfterRsync;
        return this;
    }

    //sync命令同步指令选项  -az
    public PageStaticBuilder rsyncOptions(String rsyncOptions) {
        if (StringUtils.isNotEmpty(rsyncOptions)) {
            this.rsyncOptions = rsyncOptions;
        }
        return this;
    }

    //设置url调用的最大线程数
    public PageStaticBuilder maxUrlContentGeneratingThreads(int maxGeneratingThreads) {
        if (maxGeneratingThreads > 0) {
            this.maxGeneratingThreads = maxGeneratingThreads;
        }
        return this;
    }

    //设置rsync重试的次数
    public PageStaticBuilder rsyncRetryTimes(int rsyncRetryTimes) {
        this.rsyncRetryTimes = rsyncRetryTimes;
        return this;
    }

    //httpsocket响应时间
    public PageStaticBuilder httpSocketTimeoutSeconds(int httpSocketTimeoutSeconds) {
        if (httpSocketTimeoutSeconds > 0) {
            this.httpSocketTimeoutSeconds = httpSocketTimeoutSeconds;
        }
        return this;
    }

    //rsync同步的响应时间
    public PageStaticBuilder rsyncTimeoutSeconds(int rsyncTimeoutSeconds) {
        if (rsyncTimeoutSeconds > 0) {
            this.rsyncTimeoutSeconds = rsyncTimeoutSeconds;
        }
        return this;
    }

    //http代理
    public PageStaticBuilder httpProxy(String proxyHost, int proxyPort) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        return this;
    }

    //rsync完成监听
    public PageStaticBuilder rsyncCompleteListener(RsyncCompleteListener rsyncCompleteListener) {
        this.rsyncCompleteListener = rsyncCompleteListener;
        return this;
    }

    //httpclient完成监听
    public PageStaticBuilder httpClientCompleteListener(HttpClientCompleteListener httpClientCompleteListener) {
        this.httpClientCompleteListener = httpClientCompleteListener;
        return this;
    }

    //临时文件夹
    public PageStaticBuilder tempDir(String tempDir) {
        this.tempDir = tempDir;
        return this;
    }

    //logger设置
    public PageStaticBuilder logger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
        return this;
    }
    public PageStaticBuilder logger(String str) {
        this.logger = LoggerFactory.getLogger(str);
        return this;
    }

    /**
     * 验证配置
     */
    private void validateConfig() {
        if (rsyncDirs.size() == 0 || rsyncRemotes.size() == 0) {
            throw new RuntimeException("至少有一个addRsyncRemote和addRsyncDir的配置");
        }
    }

    /**
     * get 方法
     */
    public List<RsyncRemote> getRsyncRemotes() {
        return rsyncRemotes;
    }

    public List<RsyncDir> getRsyncDirs() {
        return rsyncDirs;
    }

    public int getUploadTriggerMaxFiles() {
        return uploadTriggerMaxFiles;
    }

    public int getUploadTriggerMaxSeconds() {
        return uploadTriggerMaxSeconds;
    }

    public boolean isDeleteLocalDirAfterRsync() {
        return deleteLocalDirAfterRsync;
    }

    public int getMaxGeneratingThreads() {
        return maxGeneratingThreads;
    }

    public int getHttpSocketTimeoutSeconds() {
        return httpSocketTimeoutSeconds;
    }

    public int getRsyncTimeoutSeconds() {
        return rsyncTimeoutSeconds;
    }

    public RsyncCompleteListener getRsyncCompleteListener() {
        return rsyncCompleteListener;
    }

    public HttpClientCompleteListener getHttpClientCompleteListener() {
        return httpClientCompleteListener;
    }

    public String getRsyncOptions() {
        return rsyncOptions;
    }

    public String getTempDir() {
        return tempDir;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public int getRsyncRetryTimes() {
        return rsyncRetryTimes;
    }

    public Logger getLogger() {
        return logger;
    }
}
