package com.ing.springboot.training.d03.s01.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * A simple {@link javax.sql.DataSource} configuration, which configures the JPA repositories,
 * using the {@link EnableJpaRepositories} annotation
 *
 * @author bogdan.solga
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.ing.springboot.training.d03.s01.repository")
@EntityScan(basePackages = "com.ing.spring.training.jpa.model")
@EnableTransactionManagement
public class DataSourceConfig {
}
