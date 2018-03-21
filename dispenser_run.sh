#!/bin/bash

source=dockerConfig

VERSION=0.2.3-dev

dispenser_run_database(){
   if [ !  "(docker ps -aq -f status=exited -f name=dispenser-mongo)" ]; then
      docker start dispenser-mongo;
   elif [ ! "(docker ps -aq -f status=running -f name=dispenser-mongo)" ]; then
      docker run --name dispenser-mongo -d mongo;
   fi
}

dispenser_run_docker(){
   if [ "(docker ps -aq -f status=running -f name=dispenser" ]; then
      #clean up
      docker stop dispenser
      docker rm dispenser
   elif [ "(docker ps -aq -f status=exited -f name=dispenser" ]; then
      docker rm dispenser
   fi
   if [ -z "$PORT"  ]; then
      docker run --name dispenser -p 4000:9000 --link dispenser-mongo:mongo -d vivaconagua/dispenser:${VERSION} \
         -Dplay.evolutions.db.default.autoApply=true \
   -Dplay.http.secret.key"ösjkadfhkjsadfaösjdfnisajdnfsöjkadfn" \
  -Dmongodb.uri=mongodb://mongo/dispenser \
   -Dconfig.resource=application.conf \
   -Ddispenser.hostURL="http:/localhost:9000" \
   -Dplay.http.context="/";

   fi

}

dispenser_build_docker(){
   if [ -z "$dockerVERSION" ]; then
      sbt -Dversion=0.2.3-dev docker:publishLocal;
   else 
      sbt docker:publishLocal;
   fi
}

case "$1" in
   build)
      while getopts :v: option
      do
         case "${option}" in
            v) dockerVERSION=${OPTARG}
         esac
      done
      dispenser_build_docker
      ;;
   run)
      while getopts :p:v: option
         do
            case "${option}"
               in
               p) PORT=${OPTARG};;
               v) ${VERSION}=${OPTARG};;
            esac
         done
         dispenser_run_database 
         dispenser_run_docker 
   ;;
   clean)
      docker rm dispenser-mongo
      docker rm dispenser
   ;;
   *)
      echo "wrong parameter"
esac



