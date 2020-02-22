#!/bin/bash
git checkout .
git pull origin master

echo "start process"

mvn -q clean package -Dmaven.test.skip=true
echo "package done"


imageName=bothub-`date +%Y%m%d`
echo $imageName

docker build -t $imageName .
echo "build image done"

containerId=`cat ~/tmp/bothub_id`
docker stop $containerId
echo "stop $containerId"
sleep 5

docker run -d -p 9527:9527 $imageName > ~/tmp/bothub_id

echo `cat ~/tmp/bothub_id`
echo "run new instance done"
