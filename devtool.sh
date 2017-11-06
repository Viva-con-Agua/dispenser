#!/bin/bash
source ~/.bashrc
npm run build
sbt docker:publishLocal
pool dispenser rm
pool dispenser run

