#!/bin/bash

mvn clean && mvn package -Dmaven.test.skip=true && mvn antrun:run