# Sproner
A RESTful API for retriving and uploading song data done with SpringBoot, Hibernate and Postgresql. \
My intention here is to create the end-point of a music service. \
In the next table are described the Methods, URLs and their corresponding actions:

| Method | URL | Action |
| ------ | --- | ------ |
| _POST_ | [x] **/api/song** | Create a new song |
| _GET_ | [x] **/api/song** | Retrieve all songs |
| _DELETE_ | [x] **/api/song** | Delete all songs |
| _GET_ | [x] **/api/song/id:** | Retrieve a song by id |
| _PUT_ | [x] **/api/song/id:** | Update a song by id |
| _DELETE_ | [x] **/api/song/id:** | Delete a song by id |
| _GET_ | [x] **/api/song?title=[keyword]** | Retrive a song containing **keyword** in its title |

## Setting the database

Before doing anything is needed to create the database for our project and grant permissions on it. \
For this I'll run the following command inside psql, on an superuser account:
```
CREATE TABLESPACE sproner_music 
OWNER 'dba'
LOCATION 'C:\pdbs\sproner';

CREATE DATABASE musiclibrary
WITH 
ENCODING='UTF8' 
OWNER='dba'
TABLESPACE='sproner_music';

GRANT ALL PRIVILEGES
ON DATABASE musiclibrary
TO dba;
```

As I said it's required to run the previous commands on a superuser account. For example using:
```
psql -U postgres -W -d postgres -a -f structure.sql
```
Then I'll create two schemas:
```
/* Inside the musiclibrary database (psql -U dba -W -d musiclibrary) */
CREATE SCHEMA music;
CREATE SCHEMA users;
SET search_path TO music, users , public;
```

## Setting the configuration files
In the project's pom.xml add:
```
<dependency>
	<groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
	<scope>runtime</scope>
</dependency>
<!-- For extra data types like Postgres's interval -->
<dependency>
    <groupId>com.vladmihalcea</groupId>
    <artifactId>hibernate-types-52</artifactId>
    <version>2.9.7</version>
</dependency>
```
_Please check [this](https://www.baeldung.com/hibernate-types-library) 
and [this](https://vladmihalcea.com/map-postgresql-interval-java-duration-hibernate/) for the last
dependency._ \
And verify that the dependencies
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
Are there, if not add them. \
For Spring documentation see [_Queries_](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods) and [_Example_](https://bezkoder.com/spring-boot-postgresql-example/).

https://sproner.herokuapp.com/ | https://git.heroku.com/sproner.git

