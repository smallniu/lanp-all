package org.pq.demo.javaCore.introspector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtilsTest {
    public static void main(String[] args) throws Exception {
        UserInfo userInfo = new UserInfo();
        //1.字段username值
        BeanUtils.setProperty(userInfo, "userName", "parker");
        System.out.println("set userName:" + userInfo.getUserName());
        System.out.println("get username:" + BeanUtils.getProperty(userInfo, "userName"));
        //2.字段age值
        BeanUtils.setProperty(userInfo, "age", 18);
        System.out.println("set age:" + userInfo.getAge());
        System.out.println("get age:" + BeanUtils.getProperty(userInfo, "age"));
        //3.字段username,age类型
        System.out.println("get username type:" + BeanUtils.getProperty(userInfo, "userName").getClass().getName());
        System.out.println("get age type:" + BeanUtils.getProperty(userInfo, "age").getClass().getName());
        //4.字段age值改变时
        PropertyUtils.setProperty(userInfo, "age", 8);
        System.out.println(PropertyUtils.getProperty(userInfo, "age"));
        System.out.println(PropertyUtils.getProperty(userInfo, "age").getClass().getName());

        //5.字段age 赋值改为字符串时，此时会发生异常
        PropertyUtils.setProperty(userInfo, "age", "8");

        //6.日期
        BeanUtils.setProperty(userInfo, "birthday.time", "111111");
        Object obj = BeanUtils.getProperty(userInfo, "birthday.time");
        System.out.println(obj);
    }
    /**
     * 1.获得属性的值，例如，BeanUtils.getProperty(userInfo,"userName")，返回字符串
     * 2.设置属性的值，例如，BeanUtils.setProperty(userInfo,"age",8)，参数是字符串或基本类型自动包装。
     *   设置属性的值是字符串，获得的值也是字符串，不是基本类型。
     * 3.BeanUtils的特点：
     *  1). 对基本数据类型的属性的操作：在WEB开发、使用中，录入和显示时，值会被转换成字符串，但底层运算用的是基本类型，这些类型转到动作由BeanUtils自动完成。
     *  2). 对引用数据类型的属性的操作：首先在类中必须有对象，不能是null，
     *    例如，private Date birthday=new Date(); 操作的是对象的属性而不是整个对象，
     *    例如，BeanUtils.setProperty(userInfo,"birthday.time",111111);
     * 4.PropertyUtils类和BeanUtils不同在于，运行getProperty、setProperty操作时，没有类型转换，
     * 使用属性的原有类型或者包装类。由于age属性的数据类型是int，所以方法PropertyUtils.setProperty(userInfo, "age", "8")
     * 会爆出数据类型不匹配，无法将值赋给属性。
     */
}
