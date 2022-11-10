package com.ing.springboot.training.d04.s06;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class SampleCassandraTest {

    private static final int PORT = 9042;

    private static final String KEYSPACE_NAME = "spring_boot_cassandra_demo";

    @Container
    private static final CassandraContainer<?> CASSANDRA_CONTAINER =
            (CassandraContainer) new CassandraContainer("cassandra:4.1").withExposedPorts(PORT);

    @BeforeAll
    public static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.data.cassandra.port",
                String.valueOf(CASSANDRA_CONTAINER.getMappedPort(PORT)));

        try (Cluster cluster = createCluster();
             Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME +
                    " WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Cluster createCluster() {
        return Cluster.builder()
                      .addContactPoint(CASSANDRA_CONTAINER.getHost())
                      .withPort(CASSANDRA_CONTAINER.getMappedPort(PORT))
                      .build();
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
