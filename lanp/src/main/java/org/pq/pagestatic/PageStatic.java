package org.pq.pagestatic;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.pq.pagestatic.util.PageStaticUtils;
import org.pq.pagestatic.base.PageService;
import org.pq.pagestatic.bean.Page;
import org.pq.pagestatic.bean.PageState;
import org.pq.pagestatic.impl.PageHttpClient;
import org.pq.pagestatic.impl.PageRsync;
import org.pq.pagestatic.impl.PageUploader;
import org.slf4j.Logger;

public class PageStatic implements PageService {
    private PageStaticBuilder pageStaticBuilder;
    private volatile BlockingQueue<Page> pageQueue;
    private volatile ExecutorService threadPool4GetUrlContent;
    private volatile PageHttpClient pageHttpClient;
    private volatile PageRsync pageRsync;
    private volatile PageUploader pageUploader;
    private volatile PageState pageState;
    private Logger log;
    private File tempDir;

    PageStatic(PageStaticBuilder pageStaticBuilder) {
        this.pageStaticBuilder = pageStaticBuilder;
        log = pageStaticBuilder.getLogger();
    }

    @Override
    public void shutdown() {
        threadPool4GetUrlContent = null;
        if (pageHttpClient != null) {
            pageHttpClient.shutdown();
            pageHttpClient = null;
        }
        pageUploader = null;
        pageQueue = null;
        pageRsync = null;
        pageState.setEndupMillis(System.currentTimeMillis());
        log.info("{} page static shutted down!, cost {} seconds", pageState.getBatchId(),
                pageState.getCostMillis() / 1000.);
    }

    @Override
    public boolean isTerminated() {
        return threadPool4GetUrlContent.isTerminated() && pageQueue.isEmpty();
    }

    /**
     * 准备上传
     * @param url
     * @param content
     * @param file
     */
    public void startupBatch() {
        createThreadPool4GetUrlContent();
        createPageQueue();
        createPageUploader();
        createPageRsync();
        startupUploader();
    }

    /**
     * 可选择上传的方式：
     * 1.直接文件上传
     * 2.直接内容上传
     * 3.调用url方式上传
     */
    public void directUpload(File localFileName) {
        putQueue("direct://", localFileName, localFileName);
    }

    public void directContentUpload(String content, String localFileName) {
        InputStream is = IOUtils.toInputStream(content);
        File file = PageStaticUtils.createTmpFile(log, tempDir, "direct://", localFileName, is);
        IOUtils.closeQuietly(is);

        putQueue("direct://", file, new File(localFileName));
    }

    public void urlStaticAndUpload(final String url, final String localFileName, final Object... callbackParams) {
        checkPageHttpClientCreated(url);
        threadPool4GetUrlContent.submit(new Runnable() {
            @Override
            public void run() {
                if (!pageHttpClient.executeGetMethod(url, callbackParams, localFileName))
                    return;
                putQueue(url, pageHttpClient.getContent(), new File(localFileName));
            }
        });
    }

    /**
     * 等待上传完成操作
     */
    public void finishBatch() {
        threadPool4GetUrlContent.shutdown();
        waitLastBatchFinish();
        deleteTempDir();
    }

    private void createThreadPool4GetUrlContent() {
        threadPool4GetUrlContent = Executors.newFixedThreadPool(pageStaticBuilder.getMaxGeneratingThreads());
    }

    private BlockingQueue<Page> createPageQueue() {
        return pageQueue = new LinkedBlockingQueue<Page>();
    }

    private void createPageUploader() {
        pageUploader = new PageUploader(log);
        pageUploader.setUploadTriggerMaxFiles(pageStaticBuilder.getUploadTriggerMaxFiles());
        pageUploader.setUploadTriggerMaxSeconds(pageStaticBuilder.getUploadTriggerMaxSeconds());
        pageUploader.setPageService(this);
        pageUploader.setPageQueue(pageQueue);
    }

    private void createPageRsync() {
        pageRsync = new PageRsync(log);
        pageRsync.setRsyncRemotes(pageStaticBuilder.getRsyncRemotes());
        pageRsync.setRsyncDirs(pageStaticBuilder.getRsyncDirs());
        pageRsync.setRsyncOptions(pageStaticBuilder.getRsyncOptions());
        pageRsync.setDeleteLocalDirAfterRsync(pageStaticBuilder.isDeleteLocalDirAfterRsync());
        pageRsync.setRsyncTimeoutSeconds(pageStaticBuilder.getRsyncTimeoutSeconds());
        pageRsync.setRsyncCompleteListener(pageStaticBuilder.getRsyncCompleteListener());
        pageRsync.setRsyncRetryTimes(pageStaticBuilder.getRsyncRetryTimes());
    }

    private void startupUploader() {
        pageUploader.startUpload(pageRsync);
        pageState = new PageState();
        log.info("{} page static started up!", pageState.getBatchId());
        createTmpDir();
    }

    private void createTmpDir() {
        tempDir = new File(StringUtils.defaultIfBlank(pageStaticBuilder.getTempDir(), pageState.getBatchId()));
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            log.error("mkdir fail {}", tempDir);
            throw new RuntimeException("unable to create temp dir" + tempDir);
        }
    }

    private void putQueue(String url, File content, File file) {
        try {
            pageQueue.put(new Page(url, content, file));
        } catch (InterruptedException e) {
            log.error("put queue catched InterruptedException", e);
        }
    }

    private void checkPageHttpClientCreated(String url) {
        if (pageHttpClient != null)
            return;

        pageHttpClient = new PageHttpClient(log);
        pageHttpClient.setTempDir(tempDir);
        pageHttpClient.setHttpSocketTimeoutSeconds(pageStaticBuilder.getHttpSocketTimeoutSeconds());
        pageHttpClient.setHttpClientCompleteListener(pageStaticBuilder.getHttpClientCompleteListener());
        pageHttpClient.setProxyHost(pageStaticBuilder.getProxyHost());
        pageHttpClient.setProxyPort(pageStaticBuilder.getProxyPort());
        pageHttpClient.setMaxGeneratingThreads(pageStaticBuilder.getMaxGeneratingThreads());
        pageHttpClient.startup();
    }

    private void waitLastBatchFinish() {
        while (pageRsync != null) {
            PageStaticUtils.sleepMilis(100);
        }
    }

    private void deleteTempDir() {
        PageStaticUtils.deleteDirRecursively(tempDir);
    }
    
    
    public PageState getPageState() {
        return pageState;
    }
}
