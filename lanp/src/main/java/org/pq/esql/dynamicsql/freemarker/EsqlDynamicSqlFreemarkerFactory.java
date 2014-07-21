package org.pq.esql.dynamicsql.freemarker;

import org.pq.esql.dynamicsql.EsqlDynamicSqlFactory;
import org.pq.esql.dynamicsql.EsqlDynamicSqlParsable;
import org.pq.esql.bean.EsqlPart;

import java.util.List;

public class EsqlDynamicSqlFreemarkerFactory implements EsqlDynamicSqlFactory {
    @Override
    public EsqlDynamicSqlParsable createParser() {
        return new EsqlDynamicSqlFreemarkerParser();
    }

    @Override
    public EsqlPart createSqlPart(List<String> dynamicSqlLines, String dynamicSqlLinesKey) {
        return new EsqlDynamicSqlFreemarkerSqlPart(dynamicSqlLines, dynamicSqlLinesKey);
    }
}

