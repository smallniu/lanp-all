package org.pq.pagestatic.impl;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.pq.pagestatic.impl.PageRsync;
import org.pq.pagestatic.util.PageStaticUtils;
import org.pq.pagestatic.base.PageService;
import org.pq.pagestatic.bean.Page;
import org.slf4j.Logger;
/*
 *  页面文件生成与上传类。
 */
public class PageUploader {
    private Logger log;
    private BlockingQueue<Page> pageQueue;
    private PageService pageService;
    private PageUploadTrigger uploadTrigger = new PageUploadTrigger();
    private PageRsync pageRsync;
    
    public PageUploader(Logger log) {
        this.log = log;
    }

    public void startUpload(PageRsync pageRsync) {
        this.pageRsync = pageRsync;
        pageRsync.initialize();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createFileAndRsyncUpload();
                }
                catch (Exception ex) {
                    log.error("createFileAndRsyncUpload has an error!", ex);
                }
            }
        }).start();
    }
    /**
     * 创建文件和同步文件
     */
    private void createFileAndRsyncUpload() {
        uploadTrigger.reset();

        while (true) {
            //检查是否达到 触发上传的条件，如果达到触发条件，就触发执行rsync命令
            checkUploadTriggered();
            //弹出队列并创建文件
            while (pollQueueAndCreateFile());
            //判断是否停止的条件
            if (pageService.isTerminated()) break;
        }

        log.info("page uploader going to shutdown!");

        pageRsync.rsync(uploadTrigger);
        pageService.shutdown();
        log.info("page uploader shutted down after processed {} files with {} seconds!",
                uploadTrigger.getTotalFileCounting(), uploadTrigger.getTotalCostSeconds());
    }

    private void checkUploadTriggered() {
        if (!uploadTrigger.reachTrigger()) return;

        pageRsync.rsync(uploadTrigger);
    }

    private boolean pollQueueAndCreateFile()  {
        Page page = pollQueue();
        if (page == null) return false;

        int fileCount = PageStaticUtils.createFile(log, page);
        uploadTrigger.incrFileCounting(fileCount);

        return true;
    }

    private Page pollQueue()  {
        try {
            return pageQueue.poll(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("poll queue catched InterruptedException {}", e);
            return null;
        }
    }

    public void setPageQueue(BlockingQueue<Page> pageQueue) {
        this.pageQueue = pageQueue;
    }

    public void setUploadTriggerMaxFiles(int uploadTriggerMaxFiles) {
        uploadTrigger.setUploadTriggerMaxFiles(uploadTriggerMaxFiles);
    }

    public void setUploadTriggerMaxSeconds(int uploadTriggerMaxSeconds) {
        uploadTrigger.setUploadTriggerMaxSeconds(uploadTriggerMaxSeconds);
    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }
    
}
