package org.pq.pagestatic.base;

import org.pq.pagestatic.bean.RsyncRunInfo;


public interface RsyncCompleteListener {
    void onComplete(RsyncRunInfo rsyncRunInfo);
}
