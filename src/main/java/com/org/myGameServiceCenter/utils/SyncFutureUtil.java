package com.org.myGameServiceCenter.utils;

import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

public class SyncFutureUtil {
    static private final Logger logger = Logger.getLogger(SyncFutureUtil.class);
    private ConcurrentHashMap<String, SyncFuture<Object>> syncFutureMap = new ConcurrentHashMap<String, SyncFuture<Object>>();
    private int syncFutureCount = 10;
    private long invalidTime = 1000 * 30;

    public SyncFutureUtil(int syncFutureCount, long invalidTime) {
        this.syncFutureCount = syncFutureCount;
        this.invalidTime = invalidTime;
    }

    public void removeSyncFuture(String contentId) {
        SyncFuture<Object> syncFutureTemp = syncFutureMap.get(contentId);
        if (syncFutureTemp != null) {
            syncFutureTemp.setResponse(null);
            syncFutureMap.remove(contentId);
        }
    }

    public void setSyncFuture(String contentId, Object object) {
        if (syncFutureMap.get(contentId) != null) {
            syncFutureMap.get(contentId).setResponse(object);
        }
    }

    public SyncFuture<Object> getSyncFuture(String contentId) {
        SyncFuture<Object> syncFuture = null;
        synchronized (SyncFutureUtil.class) {
            if (syncFutureMap.size() < syncFutureCount) {
                syncFuture = new SyncFuture<Object>();
                syncFutureMap.put(contentId, syncFuture);
            } else {
                logger.info("syncFutureMap 数量超 " + syncFutureCount + " 大小：" + syncFutureMap.size());
                for (String key : syncFutureMap.keySet()) {
                    SyncFuture<Object> syncFutureTemp = syncFutureMap.get(key);
                    if ((System.currentTimeMillis() - syncFutureTemp.getBeginTime()) > invalidTime) {
                        syncFutureTemp.setResponse(null);
                        syncFutureMap.remove(key);
                    }
                }
                if (syncFutureMap.size() < syncFutureCount) {
                    syncFuture = new SyncFuture<Object>();
                    syncFutureMap.put(contentId, syncFuture);
                } else {
                    logger.info("syncFutureMap 数量依旧超 " + syncFutureCount + " 大小：" + syncFutureMap.size());
                    return null;
                }
            }
        }
        return syncFuture;
    }
}
