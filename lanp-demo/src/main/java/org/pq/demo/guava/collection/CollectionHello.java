package org.pq.demo.guava.collection;

import com.google.common.collect.ImmutableSet;

/*
 * 当我们需要多个索引的数据结构的时候，通常情况下，我们只能用这种丑陋的Map<FirstName, Map<LastName, Person>>
 * 来实现。为此Guava提供了一个新的集合类型－Table集合类型，来支持这种数据结构的使用场景。Table支持“row”和“column”，而且提供多种视图。
 */
public class CollectionHello {
    public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of("red", "orange", "yellow", "green", "blue",
            "purple");
}
