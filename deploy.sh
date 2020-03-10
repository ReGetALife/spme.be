#!/bin/bash -v

docker -v

docker stop jre_spme || true

docker rm jre_spme  || true

# make sure to export WORKSPACE and REPORT_DIR, e.g.
# export WORKSPACE=/home/user/spme/jenkins_node/workspace/spme_be

docker run -d -p 20202:20202 \
-v $WORKSPACE/target/be-0.0.1-SNAPSHOT.jar:/usr/spme.jar \
-v $REPORT_DIR:/usr/report \
--name jre_spme \
openjdk:8u242-jre-slim java -jar /usr/spme.jar