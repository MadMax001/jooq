package ru.madmax.test.jooq;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

public class ExceptionTranslator implements ExecuteListener {
    @Override
    public void exception(ExecuteContext context) {
        SQLDialect dialect = context.configuration().dialect();
        SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());
        context.exception(
                translator.translate(
                        "Access database using Jooq",
                        context.sql(),
                        context.sqlException()
                )
        );
    }
}
