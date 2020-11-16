[![Build Status](https://travis-ci.org/barakb/consul-client.svg?branch=main)](https://travis-ci.org/barakb/consul-client)
[![Download](https://api.bintray.com/packages/barakb/maven/consul-client/images/download.svg) ](https://bintray.com/barakb/maven/consul-client/_latestVersion)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.barakb/consul-client.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.barakb%22%20AND%20a:%22consul-client%22)
[![GitHub License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

### A Kotlin Consul client

I had 3 goals in mind when starting this work.

1. Fully async code no blocking threads.
2. Easy to compose both sequentially and concurrently
3. Minimum dependencies.
4. Small

     

To consume this project using maven add the following to your pom.xml

````Xml
<dependency>
     <groupId>com.github.barakb</groupId>
     <artifactId>consul-client</artifactId>
     <version>0.9.1</version>
</dependency>
````

Or gradle

````kotlin

implementation("com.github.barakb:consul-client:0.9.1")
````


##### Usage:
To create a Nomad client Kotlin DSL can be used.
```Kotlin
    val client = ConsulClient {
        address = "http://127.0.0.1:4646"
    }
```   
Https address can be used as well.

