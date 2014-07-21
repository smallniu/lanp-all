package org.pq.demo.guava.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class LoadingKVcacheDemo {
    /**
     * 不需要延迟处理 泛型的方式封装
     * cache的参数说明：
　　1. 大小的设置：CacheBuilder.maximumSize(long)  CacheBuilder.weigher(Weigher)  CacheBuilder.maxumumWeigher(long)
　　2. 时间：expireAfterAccess(long, TimeUnit) expireAfterWrite(long, TimeUnit)
　　3. 引用：CacheBuilder.weakKeys() CacheBuilder.weakValues()  CacheBuilder.softValues()
　　4. 明确的删除：invalidate(key)  invalidateAll(keys)  invalidateAll()
　　5. 删除监听器：CacheBuilder.removalListener(RemovalListener)
　　

　　refresh机制：
　　1. LoadingCache.refresh(K)  在生成新的value的时候，旧的value依然会被使用。
　　2. CacheLoader.reload(K, V) 生成新的value过程中允许使用旧的value
　　3. CacheBuilder.refreshAfterWrite(long, TimeUnit) 自动刷新cache
     */
    public <K, V> LoadingCache<K, V> cached(CacheLoader<K, V> cacheLoader) {
        LoadingCache<K, V> cache = CacheBuilder.newBuilder().maximumSize(2).weakKeys().softValues()
                .refreshAfterWrite(120, TimeUnit.SECONDS).expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<K, V>() {
                    public void onRemoval(RemovalNotification<K, V> rn) {
                        System.out.println(rn.getKey() + "被移除");
                    }
                }).build(cacheLoader);

        return cache;
    }

    /**
     * 通过key获取value
     */
    public LoadingCache<String, String> commonCache(final String key) throws Exception {
        LoadingCache<String, String> commonCache = cached(new CacheLoader<String, String>() {
            public String load(String key) throws Exception {
                return "hello " + key + "!";
            }
        });
        return commonCache;
    }
    
    @Test
    public void testCache() throws Exception{
        LoadingCache<String,String> commonCache = commonCache("parker");
        System.out.println("parker:" + commonCache.get("parker"));
        
        commonCache.apply("harry");
        System.out.println("harry:" + commonCache.get("harry"));
        
        commonCache.put("tom", "22");
        System.out.println("tom value="+commonCache.get("tom"));
    }
}
