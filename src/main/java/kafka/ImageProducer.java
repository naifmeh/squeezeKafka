package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;

public class ImageProducer {

    private KafkaProducer<String, byte[]> imProducer;

    public ImageProducer() {

    }
}
