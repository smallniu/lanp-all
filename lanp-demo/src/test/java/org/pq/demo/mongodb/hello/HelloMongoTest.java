package org.pq.demo.mongodb.hello;

import org.pq.demo.mongodb.hello.dao.TreeRepository;
import org.pq.demo.mongodb.hello.impl.TreeRepositoryImpl;
import org.pq.demo.mongodb.hello.pojo.Tree;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * 
 <beans>
   <bean id="Repository"
    class="main.impl.RepositoryImpl">
    <property name="mongoTemplate" ref="mongoTemplate" />
    </bean>

    <bean id="mongoTemplate" class="org.springframework.data.mongodb.core.MongoTemplate">
    <constructor-arg name="mongo" ref="mongo" />
    <constructor-arg name="databaseName" value="temp" />
    </bean>


    <bean id="mongo" class="org.springframework.data.mongodb.core.MongoFactoryBean">
    <property name="host" value="localhost" />
    <property name="port" value="27017" />
    </bean>


    <context:annotation-config />
            <!-- Scan components for annotations within the configured package -->
    <context:component-scan base-package="main">

    <context:exclude-filter type="annotation"
    expression="org.springframework.context.annotation.Configuration" />

    </context:component-scan>

 </beans>
 */
public class HelloMongoTest {
    public static void main(String[] args) {
        System.out.println("进来了");
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:/main/applicationContext.xml");
        TreeRepository repository = context.getBean(TreeRepositoryImpl.class);
        // cleanup collection before insertion
        repository.dropCollection();
        // create collection
        repository.createCollection();
        //增加
        repository.saveObject(new Tree("1", "Apple Tree", 10));
        System.out.println("1. " + repository.getAllObjects());

        //增加和查询
        repository.saveObject(new Tree("2", "Orange Tree", 3));
        System.out.println("2. " + repository.getAllObjects());
        System.out.println("Tree with id 1" + repository.getObject("1"));

        //修改
        repository.updateObject("1", "Peach Tree");
        System.out.println("3. " + repository.getAllObjects());

        //删除
        repository.deleteObject("2");
        System.out.println("4. " + repository.getAllObjects());
    }
}
