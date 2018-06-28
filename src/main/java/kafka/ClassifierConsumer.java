package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import utils.KafkaConfig;

import java.util.Collections;

public class ClassifierConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ClassifierConsumer.class.getSimpleName());

    private KafkaConsumer<String, byte[]> mKafkaConsumer;

    private String mGroupId;
    private String mBrokers;
    private String mTopic = KafkaConstants.CLASSIFIER_TOPIC_NAME;

    public ClassifierConsumer(String brokers) {
        mBrokers = brokers;
        mKafkaConsumer = (new KafkaConfig(mBrokers,mTopic)).getClassifierKafkaConsumer();
        mKafkaConsumer.subscribe(Collections.singletonList(mTopic));
    }

    public ClassifierConsumer(String brokers, String groupid) {
        mBrokers = brokers;
        mGroupId = groupid;
        mKafkaConsumer = (new KafkaConfig(mBrokers,mTopic,mGroupId)).getClassifierKafkaConsumer();
        mKafkaConsumer.subscribe(Collections.singletonList(mTopic));
    }

    public void consumeClassifier() {
        ConsumerRecords<String, byte[]> records = mKafkaConsumer.poll(1000);
        for(ConsumerRecord<String, byte[]> record :  records) {
            logger.debug("Received classifier.");
            if(record.value() != null) saveClassifier(record.key(),record.value());
        }
    }

    private void saveClassifier(String key, byte[] value) {
        String filePath = KafkaConstants.PATH_TO_CLASSIFIER+'/'+key;
        FileUtils.saveFileBytes(filePath,value);
    }

}
