package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.KafkaConfig;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class VideoConsumer {
    private static final Logger logger = LoggerFactory.getLogger(VideoConsumer.class.getSimpleName());

    private KafkaConsumer<String, String> vidConsumer;

    private String mGroupId;
    private String mBrokers;
    private String mTopic = KafkaConstants.VIDEO_RESULT_TOPIC_NAME;

    public VideoConsumer(String brokers, String groupId) {
        mGroupId = groupId;
        mBrokers = brokers;
        vidConsumer = (new KafkaConfig(mBrokers,mTopic,mGroupId)).getVideoKafkaConsumer();
        vidConsumer.subscribe(Collections.singletonList(mTopic));
    }

    public void consumerVideoResult() {
        ConsumerRecords<String, String> records = vidConsumer.poll(1000);
        for(ConsumerRecord<String , String> record : records) {
            //logger.info("Received message topic "+ record.topic()+" , parition "+record.partition()+", offset "+record.offset());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy @ hh:mm:ss");
            String date = simpleDateFormat.format(new Date(Long.parseLong(record.key())));
            System.out.println(record.value()+" @ "+date);
        }
        vidConsumer.commitSync();
    }
}
