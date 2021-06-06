package com.ddaying.kakaopay.keysystem.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Conditional(MemoryProfileCondition.class)
@TestConfiguration
@EnableJpaRepositories(basePackages ={"com.ddaying.kakaopay.keysystem"})
@EnableTransactionManagement
public class RepositoryTestConfig {

    @Bean
    public DataSource dataSource() {
        return getEmbeddedDatabaseBuilder()
                .setType(H2)
                .addScript("init-schema.sql")
                .build();
    }

    private EmbeddedDatabaseBuilder getEmbeddedDatabaseBuilder() {
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        databaseBuilder.setDataSourceFactory(new DataSourceFactory() {
            private final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

            @NonNull
            @Override
            public ConnectionProperties getConnectionProperties() {
                return new ConnectionProperties() {
                    @Override
                    public void setDriverClass(@NonNull Class<? extends Driver> driverClass) {
                        dataSource.setDriverClass(org.h2.Driver.class);
                    }

                    @Override
                    public void setUrl(@NonNull String url) {
                        dataSource.setUrl("jdbc:h2:mem:insurance;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false");
                    }

                    @Override
                    public void setUsername(@NonNull String username) {
                        dataSource.setUsername("sa");
                    }

                    @Override
                    public void setPassword(@NonNull String password) {
                        dataSource.setPassword("");
                    }
                };
            }

            @NonNull
            @Override
            public DataSource getDataSource() {
                return dataSource;
            }
        });

        return databaseBuilder;
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(DIALECT, "org.hibernate.dialect.H2Dialect");
        properties.setProperty(PHYSICAL_NAMING_STRATEGY, "com.ddaying.kakaopay.keysystem.config.db.LowerUnderScoreNamingStrategy");
        properties.setProperty(FORMAT_SQL, "true");
        properties.setProperty(SHOW_SQL, "true");

        return properties;
    }

}
