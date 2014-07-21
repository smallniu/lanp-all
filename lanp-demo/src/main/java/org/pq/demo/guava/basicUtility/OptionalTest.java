package org.pq.demo.guava.basicUtility;

import java.util.Set;

import org.junit.Test;

import com.google.common.base.Optional;

/**
 * 1,大多数情况下程序员使用null是为了表示某种不存在的意思，也许应该有一个value，但是这个value是空或者这个value找不到。
 *   比方说，在用不存在的key值从map中取value，Map.get返回null表示没有该map中不包含这个key。　
 *   若T类型数据可以为null，Optional<T>是用来以非空值替代T数据类型的一种方法。一个Optional对象可以包含一个非空的T引用（
 *   这种情况下我们称之为“存在的”）或者不包含任何东西（这种情况下我们称之为“空缺的”）。但Optional从来不会包含对null值的引用。
 * 2,由于这些原因，Guava库设计了Optional来解决null的问题。
 *  许多Guava的工具被设计成如果有null值存在即刻报错而不是只要上下文接受处理null值就默认使用null值继续运行。而且，Guava提供了Optional等
 *  一些工具让你在不得不使用null值的时候，可以更加简便的使用null并帮助你避免直接使用null。
 *  Optional<T>的最常用价值在于，例如，假设一个方法返回某一个数据类型，调用这个方法的代码来根据这个方法的返回值来做下一步的动作，
 *  若该方法可以返回一个null值表示成功，或者表示失败，在这里看来都是意义含糊的，所以使用Optional<T>作为返回值，则后续代码可以通过isPresent()
 *  来判断是否返回了期望的值（原本期望返回null或者返回不为null，其意义不清晰），并且可以使用get()来获得实际的返回值。
 *
 */
public class OptionalTest {
    public void testOptional() {
        Optional<Integer> possible = Optional.of(5);
        if (possible.isPresent()) {
            possible.get();//return 5
        }
    }

    /**
     * 常用静态方法：
     * 1.Optional.of(T)：获得一个Optional对象，其内部包含了一个非null的T数据类型实例，若T=null，则立刻报错。
     * 2.Optional.absent()：获得一个Optional对象，其内部包含了空值
     * 3.Optional.fromNullable(T)：将一个T的实例转换为Optional对象，T的实例可以不为空，
     *      也可以为空[Optional.fromNullable(null)，和Optional.absent()等价。
     */
    @Test
    public void testStaticMethod() {
        //1.Optional.of(T)：获得一个Optional对象，其内部包含了一个非null的T数据类型实例，若T=null，则立刻报错。
        Optional<Integer> possible = Optional.of(5);
        if (possible.isPresent()) {
            System.out.println(possible.get());//5
        }

        //2.Optional.absent()：获得一个Optional对象，其内部包含了空值
        Optional<Integer> absentOpt = Optional.absent();
        System.out.println(absentOpt.isPresent());//false

        //3.fromNullable
        Optional<Integer> NullableOpt = Optional.fromNullable(null);
        Optional<Integer> NoNullableOpt = Optional.fromNullable(10);
        System.out.println(NullableOpt.isPresent()); //false
        System.out.println(NoNullableOpt.isPresent()); //true
    }

    /**
     * 实例方法：
     * 1>. boolean isPresent()：如果Optional包含的T实例不为null，则返回true；若T实例为null，返回false
     * 2>. T get()：返回Optional包含的T实例，该T实例必须不为空；否则，对包含null的Optional实例调用get()会抛出一个IllegalStateException异常
     * 3>. T or(T)：若Optional实例中包含了传入的T的相同实例，返回Optional包含的该T实例，否则返回输入的T实例作为默认值
     * 4>. T orNull()：返回Optional实例中包含的非空T实例，如果Optional中包含的是空值，返回null，逆操作是fromNullable()
     * 5>. Set<T> asSet()：返回一个不可修改的Set，该Set中包含Optional实例中包含的所有非空存在的T实例，且在该Set中，每个T实例都是单态，如果Optional中没有非空存在的T实例，返回的将是一个空的不可修改的Set。
     */
    @Test
    public void testInstanceMethod() {
        Optional<Long> value = Optional.fromNullable(null);
        if (value.isPresent()) {
            System.out.println("get back value=" + value.get());
        } else {
            System.out.println("get back value=" + value.or(-12L));//-12
        }
        System.out.println("get back value or null:" + value.orNull());//null

        Optional<Long> valueNoNull = Optional.fromNullable(15L);
        if (valueNoNull.isPresent()) {
            Set<Long> set = valueNoNull.asSet();
            System.out.println("获得返回值 set 的 size : " + set.size());
            System.out.println("获得返回值: " + valueNoNull.get());
        } else {
            System.out.println("获得返回值: " + valueNoNull.or(-12L));
        }
        System.out.println("获得返回值 orNull: " + valueNoNull.orNull());//15
    }
}
