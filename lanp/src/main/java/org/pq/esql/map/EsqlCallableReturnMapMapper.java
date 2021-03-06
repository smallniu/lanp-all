package org.pq.esql.map;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import org.pq.esql.param.EsqlParamPlaceholder;
import org.pq.esql.param.EsqlParamPlaceholder.InOut;
import org.pq.esql.bean.EsqlSub;

import com.google.common.collect.Maps;

public class EsqlCallableReturnMapMapper implements EsqlCallableReturnMapper {

    @Override
    public Object mapResult(EsqlSub subSql, CallableStatement cs) throws SQLException {
        Map<String, Object> result = Maps.newHashMap();
        for (int i = 0, ii = subSql.getPlaceHolders().length; i < ii; ++i) {
            EsqlParamPlaceholder placeholder = subSql.getPlaceHolders()[i];
            if (placeholder.getInOut() != InOut.IN)
                result.put(placeholder.getPlaceholder(), cs.getObject(i + 1));
        }

        return result;
    }

}
