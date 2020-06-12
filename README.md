# spme.be 

<p align="left">
<a href ="https://github.com/ReGetALife/spme.be/blob/master/LICENSE"><img alt="GitHub" src="https://img.shields.io/badge/license-MIT-blue"></a>
<a href="https://travis-ci.com/ReGetALife/spme.be"><img src="https://travis-ci.com/ReGetALife/spme.be.svg?branch=master"></a>
<a href="https://docs.docker.com/docker-hub/builds/"><img alt="Docker Cloud Automated build" src="https://img.shields.io/docker/cloud/automated/getalife/spme.be"></a>
<a href="https://hub.docker.com/r/getalife/spme.be/builds"><img alt="Docker Cloud Build Status" src="https://img.shields.io/docker/cloud/build/getalife/spme.be"></a>
<a href="https://hub.docker.com/r/getalife/spme.be"><img alt="Docker Image Size (tag)" src="https://img.shields.io/docker/image-size/getalife/spme.be/latest"></a>
</p>

This is the back-end project of a simulation platform of mainframe experiment at Tongji University, which is based on z/OSMF REST APIs . 

You can check [this link](http://139.199.75.41:3000/project/11/interface/api) for more information about the interfaces provided by this project (only project developers are allowed for now).

### Related project

[spme.fe](https://github.com/ReGetALife/spme.fe)

### Getting Started

#### Prerequisites

* Java 1.8 or later
* Maven 3.6.0 or later

#### Running

```bash
mvn spring-boot:run
```

#### Packaging

```bash
mvn package
```
#### Running jar

```bash
java -jar target/be-0.0.1-SNAPSHOT.jar
```

#### Running jar with develop properties

```bash
java -jar target/be-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
#### Useful Ports

- 20200 Jenkins
- 20201 Site(front-end)
- 20202 API(back-end)
- 20203 MySQL

### Deploy with Docker

You can deploy this project to your server in ease.

```bash
docker run --name spme-be -d -p 20202:20202 -v /spme/report:/usr/report getalife/spme.be:latest
```

### Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Java Web Framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE
* [YApi](https://github.com/YMFE/yapi) - API Management
* [Travis CI](https://travis-ci.com/) - Continuous Integration
* [Jenkins(deprecated)](https://jenkins.io) - Continuous Deployment
* [Docker Hub](https://hub.docker.com) - Automated build
