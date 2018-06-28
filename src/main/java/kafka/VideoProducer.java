package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import utils.KafkaConfig;

import java.util.logging.Logger;

public class VideoProducer {
    private static final Logger logger = Logger.getLogger(VideoProducer.class.getSimpleName());

    private String mTopic;
    private String mBrokers;

    private KafkaProducer<String, String> mKafkaProducer;

    public VideoProducer(String brokers, String topic) {
        mTopic = topic;
        mBrokers = brokers;
        mKafkaProducer = (new KafkaConfig(mBrokers,mTopic)).getVideoKafkaProducer();
    }

    public void generateVideoEvent(String camid, String videoUrl) throws Exception {
        Thread t = new Thread(new VideoEventGenerator(camid.trim(),videoUrl.trim(),mKafkaProducer,mTopic));
        t.start();
    }
}
