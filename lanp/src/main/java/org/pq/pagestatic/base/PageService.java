package org.pq.pagestatic.base;

public interface PageService {
    //关闭
    void shutdown();

    //是否终止
    boolean isTerminated();
}
