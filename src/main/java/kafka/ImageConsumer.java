package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;
import utils.KafkaConfig;

import java.util.Collections;


public class ImageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(ImageConsumer.class.getName());

    private KafkaConsumer<String, byte[]> imConsumer;

    private String mGroupId;
    private String mBrokers;
    private String mTopic = ImageProducer.IMAGE_TOPIC_NAME;

    public ImageConsumer(String brokers, String groupId) {
        mGroupId = groupId;
        mBrokers = brokers;
        imConsumer = (new KafkaConfig(mBrokers,mTopic,mGroupId)).getImageKafkaConsumer();
        imConsumer.subscribe(Collections.singletonList(mTopic));
    }

    public void consumeImage() {
        ConsumerRecords<String, byte[]> records= imConsumer.poll(1000);
        for(ConsumerRecord<String , byte[]> record : records) {
            logger.info("Received message topic "+ record.topic()+" , parition "+record.partition()+", offset "+record.offset());
            saveImage(record.key(),record.value());
        }
        imConsumer.commitSync();
    }

    private void saveImage(String key, byte[] value) {
        String[] cutFilePath = FileUtils.splitFilePath(key);
        int size = cutFilePath.length;
        if(size == 0) return;
        String fileName = KafkaConstants.PATH_SAVE_IMAGE+'/'+cutFilePath[size-2]+'/'+cutFilePath[size-1];
        FileUtils.saveFileBytes(fileName,value);
    }

}
