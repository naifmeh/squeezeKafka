# Kafka application for squeezeFace

## Kafka architecture

Kafka is a distributed streaming platform mostly known for it's speed of execution and it's freedom regarding the data type to be sent. Kafka is one of the many brokers available nowadays but it is actually standing out
in the era of the data streaming because of it's ease of use and fast settings.
Kafka works based on the pub-sub architecture, implemented by an immutable commit log which to consumers can subscribe and publisher 
can publish. Kafka doesn't implement message queues such as RabbitMQ, which allows it to be fault
tolerant and scalable. The data is also disk stored and kept even when consumed so multiple consumers from different groups 
can process the data whenever they need to.
Kafka speed allows real time application and we will take advantage of this to build our streaming platform.

## Aim of this sub project :

* Provide with a image consumer and producer in order to send and save images between different servers to allow the scalability of our system.
* Provide with a classifier consumer to allow the classifier to be trained and sent across the servers.
* And the main goal is to provide a video streamer using kafka to process multiple cameras on the fly. The stream should be real-time and
not lose quality due to the sending or compressing.

## Functionalities :

The program behavior is controller by the command line inputs. There are seven types of possible behaviors :

* *Image producer* : The image producer takes an image as a -file attribute, encode it as a base64 string and process to send the picture
to the image topic.

* *Image consumer* : The image consumer read the messages on the image topic and decode the data to save the file to a predefined destination.

* *Classifier producer* : The classifier producer takes the classifier as a -file attribute input, encode it as a base64 string and send the data to the 
classifier topic.

* *Classifier consumer* : The classifier consumer define the same behavior as the image consumer.

* *Video producer* : The video producer is using OpenCV to record a video stream. The webcam url is specified as the -camurl attribute 
and the -camid define the ID of the camera under which the frames will be seen. Each frame is decomposed as a opencv Mat object (a tensor of tridimensionnal data).
The rows and columns are extracted as well as the timestamp and the frame data. Those informations are put into a json object composed of the rows, columns, data, cameraId and timestamp then 
compressed using the gzip algorithm. The data is then sent to the video stream topic.

* *Video consumer* :  The video consumer, as opposed to it's name, consume a string resulting from the main squeezeFace program, following the processing of the sent frame.
The string describes the result of the process such as an authorized face detected or no. It read from the video-result topic.

## Using the software :

The sent frames are all to be 600x480 or smaller as bigger pictures introduce a latency.
All consumers should include the -groupId as a commandline attribute.

The command line parameters are :

* -type : image-producer, image-consumer, classifier-consumer, classifier-producer, video-producer, video-consumer
* -brokers : default is localhost:9092
* -file : The file path of the file to be sent. Either an image or the classifier.
* -camid : optional. The default value is testCam
* -camurl : optional. The default value is 0
* -groupid : if the program is acting as a consumer, this option is required

The topic to be created are :

* training-img-topic
* classifier-pkl-topic
* video-result-topic
* video-stream-topic

The provided script initKafka.sh takes the Kafka path as argument and do all the dirty job such as starting the broker and creating the topics, deleting them and shutting down the server.
The second argument should either be start, delete or shutdown.
The kafka broker should be launched with the provided configuration file.

# Problems encountered :

Problem at jar generation : main class not declared
Solution : Move META-INF folder from /java to /resources

If the kafka broker doesn't start, the zookeeper is probably in fault, or the server IP in the configuration file. Also use du -sh /tmp/kafka-logs/* to check if the logs are taking too much space
which may be causing kafka to fail.
