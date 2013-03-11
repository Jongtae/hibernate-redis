# Hibernate-redis
A library for using Redis as a second level distributed cache in Hibernate.
This is an open source project.

version : 1.0.0-SNAPSHOT

## Feature
* Implement hibernate entity & query results cache using redis
* Implement read-write strategy locking with [SETNX](http://redis.io/commands/setnx)

## System Requirements

JDK 1.6 or above.

## Using Hibernate-redis

* checkout this source

        git clone git@github.com:Jongtae/hibernate-redis.git

* install locally using maven' install commend:

        mvn install -DskipTests
  
* add dependency in your project pom.xml:

        <dependency>
          <groupId>net.daum.clix</groupId>
          <artifactId>hibernate-redis</artifactId>
        </dependency>
    
* configurate hibernate properties like this :

        <property name="hibernate.cache.use_second_level_cache">true</property>
      	<property name="hibernate.cache.use_query_cache">true</property>
    	<property name="hibernate.cache.region.factory_class">net.daum.clix.hibernate.redis.RedisRegionFactory
    	</property>
        <property name="redis.host">"redis.host"</property>
    
* set your additional redis properites optionally

        <property name="redis.port">"redis.port"</property>
        <property name="redis.timeout">"redis.timeout"</property>
        <property name="redis.password">"redis.password"</property>

## License

This software is licensed under the Apache 2 license, quoted below.

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.