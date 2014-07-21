package org.pq.esql.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Test;
import org.pq.esql.Esql;

public class EsqlDemo {

    @Test
    public void getString() {
        String ss = new Esql("mysql").selectFirst("getString").execute();
        System.out.println("hello=" + ss);
    }

    /**
     * 
     public static void main(String[] args) throws Exception {
         //Class.forName("oracle.jdbc.driver.OracleDriver");
         //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@10.142.195.62:1521:EcsMall", "ecs_store", "SBS_#xTW");
         //connection.setAutoCommit(false);
         //PreparedStatement ps = connection.prepareStatement("select *  FROM DUAL");
         Class.forName("com.mysql.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.114:3306/test", "root", "hadoop");
         connection.setAutoCommit(false);
         PreparedStatement ps = connection.prepareStatement("select *  FROM temp");
         
         
         boolean execute = ps.execute();
         System.out.println(execute);
         ps.close();
         connection.close();
     }
     */
}
