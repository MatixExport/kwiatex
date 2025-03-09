<h2 align="center">
  <pre>
  __  __          _         _              
  | |/ /         (_)       | |             
  | ' /__      __ _   __ _ | |_  ___ __  __
  |  < \ \ /\ / /| | / _` || __|/ _ \\ \/ /
  | . \ \ V  V / | || (_| || |_|  __/ >  <
  |_|\_\ \_/\_/  |_| \__,_| \__|\___|/_/\_\

</pre>
</h2>

<h4 align="center">
   A garden store project made to explore capabilities of different databases.
</h4>


[![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
[![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
[![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
[![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
[![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
[![Cassandra](https://img.shields.io/badge/cassandra-%231287B1.svg?style=for-the-badge&logo=apache-cassandra&logoColor=white)](https://img.shields.io/badge/cassandra-%231287B1.svg?style=for-the-badge&logo=apache-cassandra&logoColor=white)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)](https://img.shields.io/badge/Apache%20Kafka-000?style=for-the-badge&logo=apachekafka)

<p align="center">
  <a href="#overview">Overview</a> •
  <a href="#key-features">Key Features</a> •
  <a href="#license">License</a> 
</p>


## Overview

This project is the implementation of a simple business model for a garden store. The goal of the project was to explore different types of databases:
* Postgres - a relational database
* MongoDB - a document database
* Redis - a key-value store
* Cassandra - a columnar database
* Apache Kafka - a message broker

This project was created for Non-relational databases course on Technical University of Lodz.
## Key Features
* Postgres 
  - Deployed in a single docker container
  - Use of transactions and optimistic locks
  - Use of ORM
  - Lazy loading
* MongoDB
  - Deployed as a Replica Set consisting of 3 Mongo nodes each within its own docker container
  - High availability due to data redundancy
  - Use of Json Schema
* Redis
  - Deployed in a single docker container
  - Built on top of mongo repositories(decorator pattern) to provide caching
  - Performance benchmarks to compare read/write operations with/without caching 
* Cassandra 
  - Deployed as cluster of 2 Cassandra nodes each within its own docker container
  - Data model based around and optimized for querying
  - High availability due to data redundancy
* Apache Kafka
  - Deployed as cluster consisting of 3 Kafka nodes each within its own docker container
  - Message durability by replicating and storing data across three Kafka partitions  
  - Messages are send to another application and saved in MongoDB


## License

MIT



