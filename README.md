# Workflow Java SDK

This project contains all network provision processes

- Port Provision
- L2 Connection Provision
- Aws DirectConnect Provision
- Azure DirectConnect Provision

## Development Tools
- JDK 1.8
- Maven
- Eclipse Oxygen
- Docker
- Camunda 7.7.0

## Get Started with docker

### Setup Camunda docker env

download and run camunda tomcat 7.7.0
```
docker pull camunda/camunda-bpm-platform:tomcat-7.7.0
docker run -d --name camunda -p 8080:8080 camunda/camunda-bpm-platform:7.7.0

```
Then you should be able to access:
the webapps through http://localhost:8080/camunda-welcome/index.html
```
- Username: `demo`
- Password: `demo`
```
The rest api: http://localhost:8080/engine-rest/engine

### Configure Container to use mysql
Create a db configuration file (db.properties)
```
DB_DRIVER=com.mysql.jdbc.Driver
DB_URL=jdbc:mysql://localhost:3306/camunda
DB_USERNAME=root
DB_PASSWORD=password
```
Start the container with the following command:
```
docker run -d --name camunda -p 8080:8080  --env-file db.properties camunda/camunda-bpm-platform:7.7.0
```

### Add new app into the container

```
docker run -d --name camunda -p 8080:8080 -v /home/demo/webapps/newApp.war:/camunda/webapps/newApp.war camunda/camunda-bpm-platform:7.7.0
```
### Replace the container's webapp with your own apps

```
docker run -d --name camunda -p 8080:8080 -v /home/demo/webapps/:/camunda/webapps/ camunda/camunda-bpm-platform:7.7.0
```

## Get started with docker compose

Add the following configuration into docker-compose.yml
```
camunda:
    image: docker.io/camunda/camunda-bpm-platform:tomcat-7.7.0
    network_mode: "host"
    depends_on:
      - mariadb
    volumes:
      - ./containers/camunda/server.xml:/camunda/conf/server.xml:ro,z
      - ./containers/camunda/webapps/:/camunda/webapps/:z
    #env_file:
    #  - containers/camunda/camunda.conf
    ports:
      - "8080:8080"
````
The above configuration will override the container's server.xml and webapps

## Build and deploy processes
### Build and package processes into *.war
```
mvn clean && mvn package
```
### Deploy *.war into docker container
configure `deoploy.tomcat.dir` in `build.properties`, and run the following command which will deploy all *.war into tomcat

``` 
mvn antrun:run   
```
There is a script which can build and deploy automatically
```
./deploy.sh
````
## Integrate with SonarQube
This project has been integrated with SonarQube, pls use the following scripts for code review and code report


### Code review on github PR
Need to configure `GITHUB_TOKEN` and `SONARQUBE_TOKEN`
```
./codeReview.sh $PR
```
**NOTE**: PR is the github pull request number

### Generate Code report in SonarQube
Need to configure `SONARQUBE_TOKEN`
```
./codeReport.sh
```