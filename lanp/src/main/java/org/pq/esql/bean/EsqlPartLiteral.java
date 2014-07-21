package org.pq.esql.bean;

public class EsqlPartLiteral implements EsqlPart {
    private String sqlPart;

    public EsqlPartLiteral(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    public String getSqlPart(Object bean) {
        return sqlPart;
    }

}
