1. Create a Docker container:
```
docker run -p 5432:5432 --name postgres -e POSTGRES_PASSWORD=spring-boot -d postgres:15-bullseye
```
2. Connect to the PosgreSQL container:
```
docker exec -it postgres psql -U postgres
```
3. Create the user and database:
```
CREATE USER spring_boot_admin WITH PASSWORD 'spring_boot_admin';

CREATE DATABASE spring_boot;
GRANT ALL PRIVILEGES ON DATABASE spring_boot TO spring_boot_admin;

\c spring_boot
GRANT ALL ON SCHEMA public TO spring_boot_admin;
```