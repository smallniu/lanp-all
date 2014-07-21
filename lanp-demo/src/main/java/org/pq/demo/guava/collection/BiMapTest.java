package org.pq.demo.guava.collection;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class BiMapTest {
    @Test
    public void shouldInverseBiMap() throws Exception {
        BiMap<Integer, String> bimap = HashBiMap.create();
        bimap.put(1, "one");
        bimap.put(2, "two");
        bimap.put(10, "ten");

        System.out.println(bimap.get(1));//one

        BiMap<String, Integer> inversedBiMap = bimap.inverse();
        System.out.println(inversedBiMap.get("one"));//1
    }

    @Test
    public void shouldNotAllowToPutExistingValue() throws Exception {
        BiMap<Integer, String> bimap = HashBiMap.create();

        // when
        bimap.put(1, "one");
        bimap.put(2, "two");
        bimap.put(10, "ten");
        bimap.put(10, "one");
        fail("Should throw IllegalArgumentException");
        //IllegalArgumentException:value already present: one
    }

    @Test
    public void shouldAllowToPutExistingValueWithForcePut() throws Exception {

        BiMap<Integer, String> bimap = HashBiMap.create();

        // when
        bimap.put(1, "one");
        bimap.put(2, "two");
        bimap.put(10, "ten");
        bimap.forcePut(10, "one");

        System.out.println(bimap.get(10));//one
        System.out.println(bimap.get(1)); //null

    }
}
