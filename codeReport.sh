#!/bin/bash

#generate code quality in sonarqube
#SONARQUBE_TOKEN and GITHUB_TOKEN need to be configured

mvn clean && mvn test
mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dmaven.test.failure.ingore=true -Dsonar.jacoco.reportMissing.force.zero=true -Dsonar.host.url=https://sq.ci.consoleconnect.com