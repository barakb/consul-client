[![Build Status](https://travis-ci.org/barakb/consul-client.svg?branch=master)](https://travis-ci.org/barakb/consul-client)
[![Download](https://api.bintray.com/packages/barakb/maven/consul-client/images/download.svg) ](https://bintray.com/barakb/maven/consul-client/_latestVersion)
### A Kotlin Consul client

I had 3 goals in mind when starting this work.

1. Fully async code no blocking threads.
2. Easy to compose both sequentially and concurrently
3. Minimum dependencies.
4. Small

- Choosing the underlining http client to be Apache HttpAsyncClient **satisfy the first and third requirements**.
- Extending `CloseableHttpAsyncClient.execute` as a suspend function (in the file `CloseableHttpAsyncClientExt.kt`)
  enable easy composition of the result client sequentially and concurrently, hence **satisfy the second requirement**. 

     

To consume this project using maven add the following to your pom.xml

````Xml
<dependency>
     <groupId>com.github.barakb</groupId>
     <artifactId>consul-client</artifactId>
     <version>0.9.0</version>
</dependency>
````

Or gradle

````kotlin

implementation("com.github.barakb:consul-client:0.9.0")
````


##### Usage:
To create a Nomad client Kotlin DSL can be used.
```Kotlin
    val client = ConsulCliet {
        address = "http://127.0.0.1:4646"
    }
```   
Https address can be used as well.
The authToken is optional.

