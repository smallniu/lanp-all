package org.pq.esql.bean;

import java.util.List;

import org.pq.esql.param.EsqlParamPlaceholder;
import org.pq.esql.param.EsqlPlaceholderType;

public class EsqlDynamic {
    private List<String> sqlPieces;
    private EsqlPlaceholderType placeholdertype;
    private EsqlParamPlaceholder[] placeholders;

    public void setSqlPieces(List<String> sqlPieces) {
        this.sqlPieces = sqlPieces;
    }

    public List<String> getSqlPieces() {
        return sqlPieces;
    }

    public EsqlPlaceholderType getPlaceholdertype() {
        return placeholdertype;
    }

    public void setPlaceholdertype(EsqlPlaceholderType placeholdertype) {
        this.placeholdertype = placeholdertype;
    }

    public EsqlParamPlaceholder[] getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(EsqlParamPlaceholder[] placeholders) {
        this.placeholders = placeholders;
    }

}
