package org.pq.esql.map;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.pq.esql.bean.EsqlSub;

public interface EsqlCallableReturnMapper {
    Object mapResult(EsqlSub subSql, CallableStatement cs) throws SQLException;
}
