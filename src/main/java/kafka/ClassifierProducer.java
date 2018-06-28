package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.KafkaConfig;

public class ClassifierProducer {
    private static Logger logger = LoggerFactory.getLogger(ClassifierProducer.class.getSimpleName());

    private String mBrokers;
    private KafkaProducer<String, byte[]> mKakfaProducer;

    public static final String CLASSIFIER_TOPIC_NAME = "classifier-pkl-topic";

    public ClassifierProducer(String brokers) {
        logger.info("Classifier topic : "+CLASSIFIER_TOPIC_NAME);

        mBrokers = brokers;
        KafkaConfig config = new KafkaConfig(mBrokers, CLASSIFIER_TOPIC_NAME);
        mKakfaProducer = config.getClassifierKafkaProducer();
    }

    public void sendClassifier(String fileName, byte[] classifier) {
        try {
            ProducerRecord<String, byte[]> record = new ProducerRecord<>(CLASSIFIER_TOPIC_NAME,fileName,classifier);
            mKakfaProducer.send(record);
            mKakfaProducer.flush();
            logger.info("Classifier "+fileName+" sent succesfully.");
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }


}
