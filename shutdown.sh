#!/bin/bash -e
ps -ef | grep lckclub.jar | grep -v grep | cut -c 9-15 | xargs kill
