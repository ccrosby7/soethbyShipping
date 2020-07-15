# soethbyShipping
    
    SoethbyShipping is a microservice application built to satisfy the requirements set forth in the interview document.
    
## How to Run

Check out the repository:

```
git clone https://github.com/ccrosby7/soethbyShipping.git
```

Change into the repository directory:

```
cd soethbyShipping
```

Run the following command to run locally in development:

```
 ./gradlew 
```
 To run inside a docker container:
or
```

```

##Usage

```
GET /api/quote/:id
curl --request GET --url http://localhost:8081/api/quote/1
```
   Returns a single quote with id.
```
DELETE /api/quote/:id
curl --request DELETE --url http://localhost:8081/api/quote/1
```
   Deletes quote with id.
```
PUT /api/quote/persist/:id
curl --request PUT --url http://localhost:8081/api/quote/persist/1
```
   Persists quote with id.
```
POST /api/quote/requestQuotes?sortKey=<price|duration>
  curl --request POST --url http://localhost:8081/api/quote/requestQuotes?sortKey=price \
        -d '{ "height": 5,
              "length": 5,
              "width": 5,
              "weight": 5,
              "origin": 
                { "firstName": "zzz",
                  "lastName": "zzz",
                  "streetName": "zzz",
                  "city": "zzz",
                  "state": "zzz",
                  "zip": 77777
               },
              "destination": 
                { "firstName": "zzz",
                  "lastName": "zzz",
                  "streetName": "zzz",
                  "city": "zzz",
                  "state": "zzz",
                  "zip": 77777,
               }
            }'
```
   Sends a request to implemented endpoints, response is loaded into the database then returned to the user.
## Building for production

### Packaging as jar

To build the final jar and optimize the soethbyShipping application for production, run:

```
./gradlew -Pprod clean bootJar
```

To ensure everything worked, run:

```
java -jar build/libs/*.jar
```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```
./gradlew -Pprod -Pwar clean bootWar
```

## Testing

To launch your application's tests, run:

```
./gradlew test integrationTest jacocoTestReport
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

Then, run a Sonar analysis:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a postgresql database in a docker container, run:

```
docker-compose -f src/main/docker/postgresql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/postgresql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./gradlew bootJar -Pprod jibDockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.10.1 archive]: https://www.jhipster.tech/documentation-archive/v6.10.1
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v6.10.1/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.10.1/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.10.1/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.10.1/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.10.1/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.10.1/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v6.10.1/setting-up-ci/
