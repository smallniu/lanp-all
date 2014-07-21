package org.pq.demo.javaCore.introspector;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 *  内省库类：PropertyDescriptor类
 *  表示JavaBean类通过存储器导出一个属性。主要方法：
    1. getPropertyType()，获得属性的Class对象;
    2. getReadMethod()，获得用于读取属性值的方法；getWriteMethod()，获得用于写入属性值的方法;
    3. hashCode()，获取对象的哈希值;
    4. setReadMethod(Method readMethod)，设置用于读取属性值的方法;
    5. setWriteMethod(Method writeMethod)，设置用于写入属性值的方法。
 *
 */
public class BeanInfoUtil {
    public static void setProperty(UserInfo userInfo, String userName) throws Exception {
        PropertyDescriptor propDesc = new PropertyDescriptor(userName, UserInfo.class);
        Method methodSetUserName = propDesc.getWriteMethod();
        methodSetUserName.invoke(userInfo, "wong");
        System.out.println("set userName:" + userInfo.getUserName());
    }

    public static void getProperty(UserInfo userInfo, String userName) throws Exception {
        PropertyDescriptor proDesc = new PropertyDescriptor(userName, UserInfo.class);
        Method methodGetUserName = proDesc.getReadMethod();
        Object objUserName = methodGetUserName.invoke(userInfo);
        System.out.println("get username:" + objUserName.toString());
    }

    public static void setPropertyByIntrospector(UserInfo userInfo, String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors == null || proDescrtptors.length <= 0)
            return;
        for (PropertyDescriptor propDesc : proDescrtptors) {
            if (propDesc.getName().equals(userName)) {
                Method methodSetUserName = propDesc.getWriteMethod();
                methodSetUserName.invoke(userInfo, "alan");
                System.out.println("set userName:" + userInfo.getUserName());
                break;
            }
        }
    }

    public static void getPropertyByIntrospector(UserInfo userInfo, String userName) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        if (proDescrtptors == null || proDescrtptors.length <= 0)
            return;
        for (PropertyDescriptor propDesc : proDescrtptors) {
            if (propDesc.getName().equals(userName)) {
                Method methodGetUserName = propDesc.getReadMethod();
                Object objUserName = methodGetUserName.invoke(userInfo);
                System.out.println("get userName:" + objUserName.toString());
                break;
            }
        }
    }
}
