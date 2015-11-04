Cucumber Reporting library is the set of Cucumber extensions to produce additional HTML reports and extend existing Cucumber runner functionality (currently based on JUnit).

**The Latest Version:** [![Cucumber Report](https://maven-badges.herokuapp.com/maven-central/com.github.mkolisnyk/cucumber-reports/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.github.mkolisnyk/cucumber-reports)

# How to include

The library is supposed to be included as Maven or Gradle dependency. 

So, in order to add this library we should add either Maven dependency like:

```xml
<dependency>
    <groupId>com.github.mkolisnyk</groupId>
    <artifactId>cucumber-reports</artifactId>
    <version>0.0.12</version>
</dependency>
```

or Gradle dependency:

```groovy
compile 'com.github.mkolisnyk:cucumber-reports:0.0.12'
```

# Features

## Cucumber Engine Extensions

### Pre- and Post-conditions

### Failed Tests Re-run

## Reports

### Overview Report

### Detailed Report

### Test Coverage Report

### Usage Report

# System Requirements

| Component | Value |
| --------- | ----- |
| Java Version | 1.8 or greater |
| Maven | 3.0 or greater |
| Cucumber Version | 1.2.2 | 
| JUnit Version | 4.11 or geater |