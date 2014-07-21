package org.pq.pagestatic.bean;

import java.io.File;

/**
 * 一个需要静态化并上传的页面.
 *
 */
public class Page {
   private String url;
   //包含Client访问页面URL返回的响应体临时文件名称
   private File tempFile;
   //响应体所需要存储的本地文件名称
   private File localFile;
   
   public Page(String url, File tempFile, File localFile) {
       this.url = url;
       this.tempFile = tempFile;
       this.localFile = localFile;
   }
   
   public String getUrl() {
       return url;
   }

   public File getTempFile() {
       return tempFile;
   }

   public File getLocalFile() {
       return localFile;
   }
}
