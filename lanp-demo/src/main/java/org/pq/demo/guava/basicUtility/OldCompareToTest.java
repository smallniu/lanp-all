package org.pq.demo.guava.basicUtility;

import java.util.Comparator;

import org.junit.Test;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class OldCompareToTest {
    @Test
    public void test1() {
        Person p = new Person("parker", 23);
        Person p1 = new Person("pker", 25);
        Person p2 = new Person("tom", 23);
        Person p3 = new Person("tom", 23);
        Person p4 = new Person("tom", 29);

        System.out.println(p.compareTo(p1));
        System.out.println(p1.compareTo(p2));
        System.out.println(p3.compareTo(p4));
    }
}

/**
 * 1. java 普通的实现比较
 *
 */
class Person implements Comparable<Person> {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person other) {
        int cmpName = name.compareTo(other.name);
        if (cmpName != 0) {
            return cmpName;
        }
        if (age > other.age) {
            return 1;
        } else if (age < other.age) {
            return -1;
        }
        return 0;
    }
}

/**
 * 2.上面的compareTo方法，代码看上去并不是十分优雅，如果实体属性很多，数据类型丰富，代码可读性将会很差。
 * 在guava里, 对所有原始类型都提供了比较的工具函数来避免这个麻烦. 比如对Integer, 可以用Ints.compare()。
 * 利用guava的原始类型的compare，我们对上面的方法做一个简化，实现compare方法：PersonComparator类
 */
class PersonComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        int result = p1.name.compareTo(p2.name);
        if (result != 0) {
            return result;
        }
        return Ints.compare(p1.age, p2.age);
    }
}

/**
 * 3.上面的代码看上去简单了一点，但还是不那么优雅简单，
 * 对此, guava有一个相当聪明的解决办法, 提供了ComparisonChain:
 *
 */
class Student implements Comparable<Student> {
    public String name;
    public int age;
    public int score;

    Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public int compareTo(Student other) {
        return ComparisonChain.start()
                .compare(name, other.name)
                .compare(age, other.age)
                .compare(score, other.score, Ordering.natural().nullsLast()).result();
    }
}

class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return ComparisonChain.start().compare(s1.name, s2.name).compare(s1.age, s2.age).compare(s1.score, s2.score)
                .result();
    }
}
