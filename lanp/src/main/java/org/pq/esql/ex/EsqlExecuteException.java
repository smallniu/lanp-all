package org.pq.esql.ex;

public class EsqlExecuteException extends EsqlException {
    private static final long serialVersionUID = -4580346119774034899L;

    public EsqlExecuteException(String msg) {
        super(msg);
    }

    public EsqlExecuteException(Throwable e) {
        super(e);
    }

    public EsqlExecuteException(String msg, Throwable e) {
        super(msg, e);
    }

}
