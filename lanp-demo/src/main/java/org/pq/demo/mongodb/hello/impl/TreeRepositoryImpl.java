package org.pq.demo.mongodb.hello.impl;

import java.util.List;

import org.pq.demo.mongodb.hello.dao.TreeRepository;
import org.pq.demo.mongodb.hello.pojo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public class TreeRepositoryImpl implements TreeRepository<Tree> {
    MongoTemplate mongoTemplate;

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //查询
    public List<Tree> getAllObjects() {
        return mongoTemplate.findAll(Tree.class);
    }

    //增加
    public void saveObject(Tree object) {
        mongoTemplate.insert(object);
    }

    public Tree getObject(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Tree.class);
    }

    public WriteResult updateObject(String id, String name) {
        return mongoTemplate.updateFirst(new Query(Criteria.where("id").is(id)), Update.update("name", name),
                Tree.class);
    }

    public void deleteObject(String id) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Tree.class);

    }

    public void createCollection() {
        if (!mongoTemplate.collectionExists(Tree.class)) {
            mongoTemplate.createCollection(Tree.class);
        }

    }

    public void dropCollection() {
        if (mongoTemplate.collectionExists(Tree.class)) {
            mongoTemplate.dropCollection(Tree.class);
        }

    }

}
