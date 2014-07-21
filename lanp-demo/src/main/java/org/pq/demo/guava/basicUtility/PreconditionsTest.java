package org.pq.demo.guava.basicUtility;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Preconditions;

/**
 * Preconditions里面的方法：

  1 .checkArgument(boolean) ：
       功能描述：检查boolean是否为真。 用作方法中检查参数
　　失败时抛出的异常类型: IllegalArgumentException

　　2.checkNotNull(T)：     
　　功能描述：检查value不为null， 直接返回value；
　　失败时抛出的异常类型：NullPointerException

　　3.checkState(boolean)：
　　功能描述：检查对象的一些状态，不依赖方法参数。 例如， Iterator可以用来next是否在remove之前被调用。
　　失败时抛出的异常类型：IllegalStateException

　　4.checkElementIndex(int index, int size)：
　　功能描述：检查index是否为在一个长度为size的list， string或array合法的范围。
 index的范围区间是[0, size)(包含0不包含size)。无需直接传入list， string或array， 只需传入大小。返回index。   
　　失败时抛出的异常类型：IndexOutOfBoundsException


　　5.checkPositionIndex(int index, int size)：
　　功能描述：检查位置index是否为在一个长度为size的list， string或array合法的范围。 
   index的范围区间是[0， size)(包含0不包含size)。无需直接传入list， string或array， 只需传入大小。返回index。
　　失败时抛出的异常类型：IndexOutOfBoundsException

　　6.checkPositionIndexes(int start, int end, int size)：
　　功能描述：检查[start, end)是一个长度为size的list， string或array合法的范围子集。伴随着错误信息。
　　失败时抛出的异常类型：IndexOutOfBoundsException
 *
 */
public class PreconditionsTest {
    public static void getPersonByPrecondition(int age, String name) {
        Preconditions.checkNotNull(name, "name为null");
        Preconditions.checkArgument(name.length() > 0, "name为''");
        Preconditions.checkArgument(age > 0, "age必须大于0");
        System.out.println("a person age:" + age + ",name:" + name);
    }

    @Test
    public void test1() throws Exception {

        getPersonByPrecondition(8, "parker");

        try {
            getPersonByPrecondition(-9, "parker");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            getPersonByPrecondition(8, "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            getPersonByPrecondition(8, null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test2() {
        List<Integer> intList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            try {
                checkState(intList, 9);
                intList.add(i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        try {
            checkPositionIndex(intList, 3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndex(intList, 13);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndexes(intList, 3, 7);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndexes(intList, 3, 17);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkPositionIndexes(intList, 13, 17);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkElementIndex(intList, 6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            checkElementIndex(intList, 16);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkState(List<Integer> intList, int index) throws Exception {
        String exMsg = "intSize size不能大于" + index;
        Preconditions.checkState(intList.size() < index, exMsg);
    }

    public static void checkPositionIndex(List<Integer> intList, int index) throws Exception {
        String exMsg = "index " + index + " 不在 list中， List size为：" + intList.size();
        Preconditions.checkPositionIndex(index, intList.size(), exMsg);
    }

    public static void checkPositionIndexes(List<Integer> intList, int start, int end) throws Exception {
        Preconditions.checkPositionIndexes(start, end, intList.size());
    }

    public static void checkElementIndex(List<Integer> intList, int index) throws Exception {
        String exMsg = "index 为 " + index + " 不在 list中， List size为： " + intList.size();
        Preconditions.checkElementIndex(index, intList.size(), exMsg);
    }
}
