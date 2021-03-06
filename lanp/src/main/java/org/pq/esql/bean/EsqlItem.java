package org.pq.esql.bean;

import org.apache.commons.lang3.StringUtils;
import org.pq.core.lang.RClass;
import org.pq.esql.parser.EsqlSubParser;
import org.pq.esql.util.EsqlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EsqlItem {
    private String sqlId;
    private Map<String, String> sqlOptions = new HashMap<String, String>();
    private List<List<EsqlPart>> sqlRawSubs = new ArrayList<List<EsqlPart>>();
    private Class<?> returnType;
    private String onerr;
    private String split;
    private List<String> rawSqlLines = new ArrayList<String>();

    public List<EsqlSub> createSqlSubs(Object bean) {
        return EsqlUtils.createSqlSubs(bean, sqlRawSubs, this);
    }

    public void addSqlParts(Map<String, EsqlItem> sqlFile, List<String> sqlLines) {
        rawSqlLines.addAll(sqlLines);
        sqlRawSubs.add(EsqlSubParser.parse(sqlFile, sqlLines, sqlId));
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public void setSqlOptions(Map<String, String> sqlOptions) {
        this.sqlOptions = sqlOptions;

        onerr = sqlOptions.get("onerr");
        returnType = RClass.loadClass(sqlOptions.get("returnType"));

        split = sqlOptions.get("split");
        if (StringUtils.isEmpty(split)) split = ";";
    }

    public String getSqlId() {
        return sqlId;
    }

    public Map<String, String> getSqlOptions() {
        return sqlOptions;
    }

    public boolean isOnerrResume() {
        return StringUtils.equals("resume", onerr);
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public String getSqlSpitter() {
        return split;
    }

    public List<String> getRawSqlLines() {
        return rawSqlLines;
    }

}
