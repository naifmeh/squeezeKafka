package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.KafkaConfig;

public class ImageProducer {

    private static Logger logger = LoggerFactory.getLogger(ImageProducer.class.getName());

    private KafkaProducer<String, byte[]> imProducer;

    public static final String IMAGE_TOPIC_NAME = "training-img-topic";

    public ImageProducer(String brokers) {
        logger.info("Image topic used : "+IMAGE_TOPIC_NAME);
        KafkaConfig config = new KafkaConfig(brokers, IMAGE_TOPIC_NAME);
        imProducer = config.setImageKafkaProducer();
    }

    public void sendImage(String imgName, byte[] image) {
        try {
            ProducerRecord<String, byte[]> record = new ProducerRecord<>(IMAGE_TOPIC_NAME,
                    imgName, image);
            imProducer.send(record);
            imProducer.flush();
            logger.info("Image "+imgName+" sent");
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }


}
