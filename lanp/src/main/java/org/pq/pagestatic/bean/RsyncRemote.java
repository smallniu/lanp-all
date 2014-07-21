package org.pq.pagestatic.bean;

/**
 * 一个rsync同步的配置
 *
 */
public class RsyncRemote {
    // 远程主机名称或者IP
    private String remoteHost;
    // 远程用户名
    private String remoteUser;
    /**
     *eg. addRsyncRemote(10.142.151.87, mall)
     * @param remoteHost
     * @param remoteUser
     */
    public RsyncRemote(String remoteHost, String remoteUser) {
        this.remoteHost = remoteHost;
        this.remoteUser = remoteUser;
    }

    public String getDestHost() {
        return remoteHost;
    }

    public String getDestUser() {
        return remoteUser;
    }


}
