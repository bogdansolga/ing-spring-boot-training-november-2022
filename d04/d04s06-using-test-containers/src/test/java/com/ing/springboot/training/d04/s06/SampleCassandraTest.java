package com.ing.springboot.training.d04.s06;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class SampleCassandraTest {

    @Container
    private static final CassandraContainer<?> CASSANDRA_CONTAINER =
            (CassandraContainer) new CassandraContainer("cassandra:4.1").withExposedPorts(9042);

    @BeforeAll
    public static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", "spring_boot_cassandra_demo");
        System.setProperty("spring.data.cassandra.port",
                String.valueOf(CASSANDRA_CONTAINER.getMappedPort(9042)));
    }

    @Test
    void givenThatTheCassandraContainerWasCreated_whenTestingIfItIsRunning_thenTheContainerIsRunning() {
        assertTrue(CASSANDRA_CONTAINER.isRunning(), "Cassandra is not running");
    }

    @AfterAll
    public static void stopTheContainer() {
        CASSANDRA_CONTAINER.close();
    }
}
