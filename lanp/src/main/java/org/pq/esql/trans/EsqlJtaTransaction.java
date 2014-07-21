package org.pq.esql.trans;

import java.io.IOException;
import java.sql.Connection;

import org.pq.esql.EsqlTran;

public class EsqlJtaTransaction implements EsqlTran {

    @Override
    public void close() throws IOException {}

    @Override
    public void start() {

    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public Connection getConn() {
        return null;
    }

}
