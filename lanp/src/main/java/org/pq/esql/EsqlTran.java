package org.pq.esql;

import java.io.Closeable;
import java.sql.Connection;

public interface EsqlTran extends Closeable{

    void start();

    void commit();
    
    void rollback();

    Connection getConn();
}
