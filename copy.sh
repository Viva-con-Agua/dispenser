#!/bin/bash

docker cp ./dispenser.js dispenser:/opt/docker/conf/dispenser/dispenser_${1}.js
