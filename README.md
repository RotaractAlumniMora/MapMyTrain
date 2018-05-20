# MapMyTrain

## Web Service

---
- spring_boot_version: 1.5.2.RELEASE
- java_version: 1.8
- tomcat_version: 8.0.43
- maven_version: 3.5
---

### How to run

```$cmd
mvn clean install
mvn spring-boot:run
```

### How to test

1. Add user to database

```$curl
curl -d '{"name":"Chanaka", "email":"ldclakmal@gmail.com"}' -H "Content-Type: application/json" -X POST http://localhost:8080/ws-mapmytrain/v1/adduser
```