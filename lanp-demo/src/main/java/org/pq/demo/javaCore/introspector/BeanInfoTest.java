package org.pq.demo.javaCore.introspector;

public class BeanInfoTest {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("parker");
        try {
            BeanInfoUtil.getProperty(userInfo, "userName");

            BeanInfoUtil.setProperty(userInfo, "userName");

            BeanInfoUtil.getProperty(userInfo, "userName");

            BeanInfoUtil.setPropertyByIntrospector(userInfo, "userName");

            BeanInfoUtil.getPropertyByIntrospector(userInfo, "userName");

            BeanInfoUtil.setProperty(userInfo, "age");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
     * 由上述可看出，内省操作非常的繁琐，所以所以Apache开发了一套简单、易用的API来操作Bean的属性——BeanUtils工具包。
     * BeanUtils工具包：下载：http://commons.apache.org/beanutils/　注意：应用的时候还
     * 需要一个logging包 http://commons.apache.org/logging/
     * 见:BeanUtilsTest.java
     */
}
