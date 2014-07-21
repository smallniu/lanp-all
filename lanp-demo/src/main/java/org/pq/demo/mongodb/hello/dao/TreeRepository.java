package org.pq.demo.mongodb.hello.dao;

import java.util.List;

import com.mongodb.WriteResult;

public interface TreeRepository<T> {
    List<T> getAllObjects();

    void saveObject(T object);

    T getObject(String id);

    WriteResult updateObject(String id, String name);

    void deleteObject(String id);

    void createCollection();

    void dropCollection();
}
