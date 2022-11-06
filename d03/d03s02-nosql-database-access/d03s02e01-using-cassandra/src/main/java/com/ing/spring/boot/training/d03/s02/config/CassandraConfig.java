package com.ing.spring.boot.training.d03.s02.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.ing.spring.boot.training.d03.s02.repository")
public class CassandraConfig {
}
