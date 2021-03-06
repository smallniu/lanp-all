package org.pq.esql.config;

import java.sql.Connection;
import java.sql.SQLException;

import org.pq.esql.EsqlTran;
import org.pq.esql.ex.EsqlConfigException;
import org.pq.esql.trans.EsqlJdbcTransaction;
import org.pq.esql.trans.EsqlJtaTransaction;

import com.google.common.base.Throwables;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class EsqlSimpleConfig implements EsqlConfigable {
    private String url;
    private String user;
    private String pass;
    private String driver;
    private String transactionType;
    private ComboPooledDataSource cpds = null;

    private Connection getConnection() {
        try {
            if (cpds == null) loadDataSource();

            return cpds.getConnection();
        } catch (SQLException e) {
            throw new EsqlConfigException("create connection fail", e);
        }
    }

    private void loadDataSource() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(driver);
            cpds.setJdbcUrl(url);
            cpds.setUser(user);
            cpds.setPassword(pass);

            // the settings below are optional -- c3p0 can work with defaults
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
        } catch (Exception ex) {
            throw Throwables.propagate(ex);
        }
    }

    @Override
    public EsqlTran getTran() {
        if ("jta".equals(transactionType)) return new EsqlJtaTransaction();

        return new EsqlJdbcTransaction(getConnection());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

}
