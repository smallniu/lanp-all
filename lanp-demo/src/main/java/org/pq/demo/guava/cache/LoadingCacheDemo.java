package org.pq.demo.guava.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * 缓存，在我们日常开发中是必不可少的一种解决性能问题的方法。简单的说，cache 就是为了提升系统性能而开辟的一块内存空间。
　　缓存的主要作用是暂时在内存中保存业务系统的数据处理结果，并且等待下次访问使用。在日常开发的很多场合，由于受限于硬盘IO的性
       能或者我们自身业务系统的数据处理和获取可能非常费时，当我们发现我们的系统这个数据请求量很大的时候，频繁的IO和频繁的逻辑处理
       会导致硬盘和CPU资源的瓶颈出现。缓存的作用就是将这些来自不易的数据保存在内存中，当有其他线程或者客户端需要查询相同的数据资源时，
       直接从缓存的内存块中返回数据，这样不但可以提高系统的响应时间，同时也可以节省对这些数据的处理流程的资源消耗，整体上来说，系统性能会有大大的提升。

　　缓存在很多系统和架构中都用广泛的应用,例如：

　　1.CPU缓存
　　2.操作系统缓存
　　3.本地缓存
　　4.分布式缓存
　　5.HTTP缓存
　　6.数据库缓存
　　等等，可以说在计算机和网络领域，缓存无处不在。可以这么说，只要有硬件性能不对等，涉及到网络传输的地方都会有缓存的身影。

　　Guava Cache是一个全内存的本地缓存实现，它提供了线程安全的实现机制。整体上来说Guava cache 是本地缓存的不二之选，简单易用，性能好。

　　Guava Cache有两种创建方式：

　　1. cacheLoader
　　2. callable callback

　　通过这两种方法创建的cache，和通常用map来缓存的做法比，不同在于，这两种方法都实现了一种逻辑——从缓存中取key X的值，如果该值已经缓存过了，
       则返回缓存中的值，如果没有缓存过，可以通过某个方法来获取这个值。但不同的在于cacheloader的定义比较宽泛，是针对整个cache定义的，可以认为是统一
       的根据key值load value的方法。而callable的方式较为灵活，允许你在get的时候指定。
 *
 */
public class LoadingCacheDemo {
    /*
     * cacheLoader方式实现
     */
    @Test
    public void TestLoadingCache() throws Exception {
        LoadingCache<String, String> cacheBuilder = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            public String load(String key) throws Exception {
                String strProvalue = "hello " + key + "!";
                return strProvalue;
            }
        });
        System.out.println("jerry value:" + cacheBuilder.apply("jerry"));
        System.out.println("jerry value:" + cacheBuilder.get("jerry"));

        System.out.println("peida value:" + cacheBuilder.get("peida"));

        System.out.println("peida value:" + cacheBuilder.apply("peida"));
        System.out.println("lisa value:" + cacheBuilder.apply("lisa"));

        cacheBuilder.put("harry", "28");
        System.out.println("harry value:" + cacheBuilder.get("harry"));
    }

    /*
     * callable callback的实现方式
     */
    @Test
    public void testCallableCache() throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("jerry", new Callable<String>() {
            public String call() {
                String strProvalue = "hello " + "jerry" + "!";
                return strProvalue;
            }
        });
        System.out.println("jerry value:" + resultVal);

        resultVal = cache.get("peida", new Callable<String>() {
            public String call() {
                String strProValue = "hello " + "peida" + "!";
                return strProValue;
            }
        });
        System.out.println("peida value : " + resultVal);
    }

   
}
