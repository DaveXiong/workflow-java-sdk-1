#!/bin/bash

#review the github pull request via sonarqube
#SONARQUBE_TOKEN and GITHUB_TOKEN need to be configured


PR=$1

if [ -z "$1" ]; then
   echo 'Usage: ./codeReview.sh PR';
   exit 1;
fi


mvn sonar:sonar -Dsonar.login=$SONARQUBE_TOKEN -Dmaven.test.failure.ingore=true -Dsonar.jacoco.reportMissing.force.zero=true -Dsonar.host.url=https://sq.ci.consoleconnect.com -Dsonar.analysis.mode=preview  -Dsonar.github.pullRequest=$PR -Dsonar.github.repository=iixlabs/workflow-java-sdk -Dsonar.github.oauth=$GITHUB_TOKEN