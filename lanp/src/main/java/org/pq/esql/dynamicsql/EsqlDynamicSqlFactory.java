package org.pq.esql.dynamicsql;

import org.pq.esql.bean.EsqlPart;

import java.util.List;

/**
 * 处理ESQL动态SQL部分的工厂类。
 */
public interface EsqlDynamicSqlFactory {
    EsqlDynamicSqlParsable createParser();

    EsqlPart createSqlPart(List<String> dynamicSqlLines, String dynamicSqlLinesKey);
}
