#!/bin/bash
KAFKA_PATH=$1

#running zookeeper
sudo service zookeeper start

#launching kafka
sudo $KAFKA_PATH/bin/kafka-server-start.sh $KAFKA_PATH/config/server.properties & 

#creating topics
`$KAFKA_PATH/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic training-img-topic`
`$KAFKA_PATH/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic classifier-pkl-topic`


