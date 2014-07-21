package org.pq.demo.guava.basicUtility;

import java.util.Comparator;

import org.junit.Test;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

public class ObjectMethodTest {

    @Test
    public void UserTest() {

        User user = new User("peida", 23, 80);
        User user1 = new User("aida", 23, 36);
        User user2 = new User("jerry", 24, 90);
        User user3 = new User("peida", 23, 80);

        System.out.println("==========equals===========");
        System.out.println(user.equals(user2));
        System.out.println(user.equals(user1));
        System.out.println(user.equals(user3));

        System.out.println("==========hashCode===========");
        System.out.println(user.hashCode());
        System.out.println(user1.hashCode());
        System.out.println(user3.hashCode());
        System.out.println(user2.hashCode());

        System.out.println("==========toString===========");
        System.out.println(user.toString());
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        System.out.println(user3.toString());

        System.out.println("==========compareTo===========");
        System.out.println(user.compareTo(user1));
        System.out.println(user.compareTo(user2));
        System.out.println(user2.compareTo(user1));
        System.out.println(user2.compareTo(user));

    }

}

class User implements Comparable<User> {
    public String name;
    public int age;
    public int score;

    User(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, age);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User that = (User) obj;
            return Objects.equal(name, that.name) && Objects.equal(age, that.age) && Objects.equal(score, that.score);
        }
        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).addValue(name).addValue(age).addValue(score).toString();
    }

    @Override
    public int compareTo(User other) {
        return ComparisonChain.start().compare(name, other.name).compare(age, other.age)
                .compare(score, other.score, Ordering.natural().nullsLast()).result();
    }
}

class UserComparator implements Comparator<User> {
    @Override
    public int compare(User s1, User s2) {
        return ComparisonChain.start().compare(s1.name, s2.name).compare(s1.age, s2.age).compare(s1.score, s2.score)
                .result();
    }
}
