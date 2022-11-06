1. Create a Docker container with MongoDB 6.0.2:
```
docker run -p 27017:27017 --name mongo -d mongo:6.0.2 
```
2. Connect to the MongoDB shell:

```
docker exec -it mongo bash
/bin/mongosh
```

3. Create the database and user:
```
use admin;

db.createUser(
   {
       user: "spring-boot-admin", 
       pwd: "aVeryComplexPassword", 
       roles:["root"]
   }
);

use spring-boot-demo;

```