package org.pq.esql.map;

import java.beans.PropertyDescriptor;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.pq.core.joor.Reflect;
import org.pq.esql.param.EsqlParamPlaceholder;
import org.pq.esql.param.EsqlParamPlaceholder.InOut;
import org.pq.esql.bean.EsqlSub;

public class EsqlCallableResultBeanMapper extends EsqlBaseBeanMapper implements EsqlCallableReturnMapper {

    public EsqlCallableResultBeanMapper(Class<?> mappedClass) {
        super(mappedClass);
    }

    @Override
    public Object mapResult(EsqlSub subSql, CallableStatement cs) throws SQLException {
        Object mappedObject = Reflect.on(this.mappedClass).create().get();

        for (int i = 0, ii = subSql.getPlaceHolders().length; i < ii; ++i) {
            EsqlParamPlaceholder placeholder = subSql.getPlaceHolders()[i];
            if (placeholder.getInOut() != InOut.IN) {
                String field = placeholder.getPlaceholder();
                PropertyDescriptor pd = this.mappedFields.get(field.toLowerCase());
                if (pd != null) {
                    Object object = cs.getObject(i + 1);
                    Reflect.on(mappedObject).set(pd.getName(), object);
                }
            }
        }

        return mappedObject;
    }

}
