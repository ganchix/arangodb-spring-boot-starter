# Spring boot starter ArangoDB [![Build Status](https://travis-ci.org/ganchix/arangodb-spring-boot-starter.svg?branch=master)](https://travis-ci.org/ganchix/arangodb-spring-boot-starter) [![codecov](https://codecov.io/gh/ganchix/arangodb-spring-boot-starter/branch/master/graph/badge.svg)](https://codecov.io/gh/ganchix/arangodb-spring-boot-starter) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.ganchix/arangodb-spring-boot-parent/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/io.github.ganchix/arangodb-spring-boot-parent) [![GitHub stars](https://img.shields.io/github/stars/badges/shields.svg?style=social&label=Star)](https://github.com/ganchix/arangodb-spring-boot-starter)

Spring boot starter for use [ArangoDB](https://www.arangodb.com/) in a Spring Boot way.

# Table of Contents
 
- [Overview](#overview)
- [Getting started](#getting-started)
- [License](#license)


### Overview

This implementation offers a way to use [ArangoDB Spring data](https://github.com/arangodb/spring-data) framework like a spring boot starter project.


### Getting started

#### Add dependency

```xml
<dependency>
    <groupId>io.github.ganchix</groupId>
    <artifactId>arangodb-spring-boot-starter</artifactId>
    <version>1.0.1</version>
</dependency>

```
#### Code example

Start your ArangoDB database, for example:

```jshelllanguage
docker run -p 8529:8529 -e ARANGO_ROOT_PASSWORD=openSesame arangodb/arangodb:3.3.5
```

Create your spring boot project and add the dependency, configure in the properties file your database, you can see [ArangoProperties](https://github.com/ganchix/arangodb-spring-boot-starter/blob/master/arangodb-spring-boot-autoconfigure/src/main/java/io/github/ganchix/arangodb/properties/ArangoProperties.java) to check all options available.

Example:

```properties
spring.data.arangodb.password=openSesame
spring.data.arangodb.database-name=test
```

And you can create your domain classes and repositories following the Spring Data Arangodb [instructions](https://github.com/arangodb/spring-data)


### License

Spring boot starter ArangoDB is licensed under the MIT License. See [LICENSE](LICENSE.md) for details.

Copyright (c) 2018 Rafael Ríos Moya
