#!/bin/bash
git checkout .
git pull origin master

echo "start process"

mvn -q clean package -Dmaven.test.skip=true
echo "package done"


#imageName=bothub-`date +%Y%m%d`
#echo $imageName
#
#docker build -t $imageName .
#echo "build image done"
#
#containerId=`cat ~/tmp/bothub_id`
#docker stop $containerId
#echo "stop $containerId"
#sleep 5
#
#docker run -d -p 9527:9527 $imageName > ~/tmp/bothub_id
ps -ef | grep bothub | grep -v grep | awk '{print $2}' | xargs kill
sleep 5

nohup java -jar target/bothub.jar 2>&1 > stdout.log &

echo "run new instance done"
