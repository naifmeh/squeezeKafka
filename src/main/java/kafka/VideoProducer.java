package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import utils.KafkaConfig;

import java.util.logging.Logger;

public class VideoProducer {
    private static final Logger logger = Logger.getLogger(VideoProducer.class.getSimpleName());

    private String mTopic;
    private String mBrokers;
    private String mCamId;

    private KafkaProducer<String, String> mKafkaProducer;

    public VideoProducer(String brokers, String topic,String camid) {
        mTopic = topic;
        mBrokers = brokers;
        mCamId = camid;
        mKafkaProducer = (new KafkaConfig(mBrokers,mTopic)).getVideoKafkaProducer();
    }

    public void generateVideoEvent(String videoUrl) throws Exception {
        Thread t = new Thread(new VideoFrameCapture(mCamId.trim(),videoUrl.trim(),mKafkaProducer,mTopic));
        t.start();
    }
}
