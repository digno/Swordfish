#!/bin/bash

mvn -DskipTests=true clean install

cd Business/Archive

mvn glassfish:deploy

cd ../../
