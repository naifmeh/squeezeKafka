package kafka;

public class KafkaConstants {

    public static final String KAFKA_IMAGE_PRODUCER_TYPE = "image-producer";
    public static final String KAFKA_IMAGE_CONSUMER_TYPE = "image-consumer";
    public static final String KAFKA_CLASSIFIER_CONSUMER_TYPE = "classifier-consumer";
    public static final String KAFKA_CLASSIFIER_PRODUCER_TYPE = "classifier-producer";
    public static final String KAFKA_VIDEO_PRODUCER_TYPE = "video-producer";

    public static final String IMAGE_TOPIC_NAME = "training-img-topic";
    public static final String CLASSIFIER_TOPIC_NAME = "classifier-pkl-topic";
    public static final String VIDEO_TOPIC_NAME = "video-stream-topic";
    public static String PATH_SAVE_IMAGE = "/home/naif/Documents/squeezeCNN/training-images";
    public static String PATH_TO_CLASSIFIER = "/home/naif/Documents/squeezeCNN/generated-embeddings";
}
