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
$ cd webservice
$ mvn clean install
$ mvn spring-boot:run
```

### How to test

Get the `API-KEY` and `ACCESS-TOKEN` of Railway API v1.0, before you test and add it to environment variables of your system as follows:

`APIKEY=<YOUR_API_KEY>`
`ACCESSTOKEN=<YOUR_ACCESS_TOKEN_OF_RAILWAY_API>`


The absolute URL should have the following structure.

`<protocol>://<host>:<port>/<context_path>/<api_version>/<api_key>/<resource_path>`


##### Add user to database

```$curl
$ curl -d '{"name":"Chanaka", "email":"ldclakmal@gmail.com"}' -H "Content-Type: application/json" -X POST http://localhost:8080/ws-mapmytrain/v1/C461D3C23C7E7264726A8D1DD5E/adduser
```