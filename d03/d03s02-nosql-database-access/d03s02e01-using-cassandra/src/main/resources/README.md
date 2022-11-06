1. Create a Docker container with Cassandra 4.1:
```
docker run -p 9042:9042 --name cassandra -d cassandra:4.1
```

2. Access the Cassandra query language shell:

```docker exec -it cassandra cqlsh```

3. Create a keyspace for the application:
```
CREATE KEYSPACE spring_boot_cassandra_demo WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
exit
```