package org.pq.demo.guava.basicUtility;
/**
 * Ordering是Guava类库提供的一个犀利强大的比较器工具，Guava的Ordering和JDK Comparator相比功能更强。它非常容易扩展，可以轻松构造复杂的comparator，然后用在容器的比较、排序等操作中。

　　本质上来说，Ordering 实例无非就是一个特殊的Comparator 实例。Ordering只是需要依赖于一个比较器（例如，Collections.max）的方法，并使其可作为实例方法。另外，Ordering提供了链式方法调用和加强现有的比较器。

　　下面我们看看Ordering中的一些具体方法和简单的使用实例。

　　常见的静态方法：

　　natural()：使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序;
　　usingToString() ：使用toString()返回的字符串按字典顺序进行排序；
　　arbitrary() ：返回一个所有对象的任意顺序， 即compare(a, b) == 0 就是 a == b (identity equality)。 本身的排序是没有任何含义， 但是在VM的生命周期是一个常量。
 *
 */
public class OrderingTest {

}
