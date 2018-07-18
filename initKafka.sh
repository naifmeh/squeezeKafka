#!/bin/bash
KAFKA_PATH=$1

if [ "$2" == "start" ];then

    #launching zookeeper
    $KAFKA_PATH/bin/zookeeper-server-start.sh $KAFKA_PATH/config/zookeeper.properties &
    #launching kafka
    $KAFKA_PATH/bin/kafka-server-start.sh $KAFKA_PATH/config/server.properties &

    #creating topics
    `$KAFKA_PATH/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic training-img-topic`
    `$KAFKA_PATH/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic classifier-pkl-topic`

    `$KAFKA_PATH/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic video-stream-topic --config retention.ms=3000 retention.bytes=100000000`
    `$KAFKA_PATH/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic video-result-topic`
elif [ "$2" == "delete" ]; then

    `bin/kafka-topics.sh --zookeeper localhost:2181  --delete --topic video-stream-topic`
    `bin/kafka-topics.sh --zookeeper localhost:2181  --delete --topic video-result-topic`
    `bin/kafka-topics.sh --zookeeper localhost:2181  --delete --topic training-img-topic`
    `bin/kafka-topics.sh --zookeeper localhost:2181  --delete --topic classifier-pkl-topic`

elif [ "$2" == "shutdown" ]; then
    $KAFKA_PATH/bin/kafka-server-stop.sh
fi
