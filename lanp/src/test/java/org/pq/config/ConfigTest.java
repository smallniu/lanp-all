package org.pq.config;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.pq.config.base.Configable;

public class ConfigTest {

    @Test
    public void test() {
        //password=323222
        assertEquals(323222, Config.getInt("password"));
        
        //[user]pass=33222
        assertEquals(33222, Config.getInt("user.pass"));
        
        //password2 = this is my ${password}
        assertEquals("this is my 323222", Config.getStr("password2"));
        
        //word1=abc
        //word2=${word1}abc
        assertEquals("abcabc", Config.getStr("word2"));
    }
    
    @Test
    public void test2(){
        /**
         * [PageStaticBuilder.DEFAULT]
         * httpSocketTimeoutSeconds(60)
         * addRsyncRemote(10.142.151.86, mall)
         * addRsyncRemote(10.142.151.87, mall)
         * addRsyncDir(/home/mall/pagestatic/pagehtml/, 10.142.151.86:/home/mall/pagestatic/)
         * addRsyncDir(/home/mall/pagestatic/pagehtml/, 10.142.151.87:/app/mallci/pagestatic/)
         */
        assertThat(Config.getStr("PageStaticBuilder.DEFAULT.httpSocketTimeoutSeconds"), is("60"));
        Configable subset = Config.subset("PageStaticBuilder.DEFAULT");
        assertThat(subset.getInt("httpSocketTimeoutSeconds"), is(60));
        System.out.println(subset.getProperties());
       Configable subset2 = subset.subset("addRsyncDir");
       System.out.println(subset2.getProperties());
    }
}
