# spme.be 

<p align="left">
<a href ="https://github.com/ReGetALife/spme.be/blob/master/LICENSE"><img alt="GitHub" src="https://img.shields.io/badge/license-MIT-blue"></a>
<a href="https://travis-ci.com/ReGetALife/spme.be"><img src="https://travis-ci.com/ReGetALife/spme.be.svg?token=xxdBSxeuzqfeBx7LkjpY&branch=master"></a>
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

### Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Java Web Framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - IDE
* [YApi](https://github.com/YMFE/yapi) - API Management
