package org.pq.esql.map;

import static org.pq.esql.util.EsqlUtils.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EsqlMapMapper implements EsqlRowMapper {

    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> row = new HashMap<String, Object>();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); ++i) {
            String column = lookupColumnName(metaData, i);
            Object value = getResultSetValue(rs, i);
            row.put(column, value);
        }

        return row;
    }

}
