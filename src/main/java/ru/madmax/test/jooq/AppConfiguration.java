package ru.madmax.test.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement
@Configuration
@RequiredArgsConstructor
public class AppConfiguration {
    private final DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider
                (new TransactionAwareDataSourceProxy(dataSource));
    }

    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(new DefaultExecuteListenerProvider(new ExceptionTranslator()));
        jooqConfiguration.set(SQLDialect.POSTGRES);

        var settings = new Settings().withRenderNameCase(RenderNameCase.LOWER);
        jooqConfiguration.set(settings);

        return jooqConfiguration;
    }

    @Bean
    public DSLContext create() {
        return new DefaultDSLContext(configuration());
    }
}
