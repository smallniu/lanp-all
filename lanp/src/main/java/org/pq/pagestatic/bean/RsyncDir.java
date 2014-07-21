package org.pq.pagestatic.bean;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * Rsync需要同步的目录配置项
 *
 */
public class RsyncDir {
    // 本地目录
    private String localDir;
    /*
     * 远程主机
     * 可能为null
     * 表示对应到所有RsyncConf中定义的相同远程主机上的remoteDir
     */
    private String remoteHost;
    // 远程主机上的目录
    private String remoteDir;
    /**
     * eg.
     * addRsyncDir(/app/localfile/,192.168.0.105:/app/mall-res/static)
     * @param localDir
     * @param remoteDir
     */
    public RsyncDir(String localDir, String remoteDir) {
        this.localDir = trim(localDir);

        if (contains(remoteDir, ":")) {
            this.remoteHost = trim(substringBefore(remoteDir, ":"));
            this.remoteDir = trim(substringAfter(remoteDir, ":"));
        } else {
            this.remoteHost = "";
            this.remoteDir = trim(remoteDir);
        }
    }

    public String getLocalDir() {
        return localDir;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public String getRemoteHost() {
        return remoteHost;
    }
}
