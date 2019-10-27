#!/bin/bash -e
java -jar lckclub.jar --spring.profiles.active=prod > log.file 2>&1 &
