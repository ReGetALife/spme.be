# zosmf.be 

<p align="left">
<img alt="GitHub" src="https://img.shields.io/github/license/ReGetALife/zosmf.be.svg?color=black">
<a href="https://travis-ci.com/ReGetALife/zosmf.be"><img src="https://travis-ci.com/ReGetALife/zosmf.be.svg?branch=master"></a>
<a href='https://coveralls.io/github/ReGetALife/zosmf.be?branch=master'><img src='https://coveralls.io/repos/github/ReGetALife/zosmf.be/badge.svg?branch=master' alt='Coverage Status' /></a>
<img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/ReGetALife/zosmf.be.svg">
</p>

This is the back-end project of a simulation platform of mainframe experiment at Tongji University, which is based on z/OSMF REST APIs . 

You can check [this link](http://139.199.75.41:3000/project/11/interface/api) for more information about the interfaces provided by this project (only project developers are allowed for now).

### Related project

[zosmf.fe](https://github.com/giuem/zosmf.fe)

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
