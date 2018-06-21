package utils;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class KafkaConfig {

    private Properties kafkaProps;
    private String kafkaBrokers;
    private String kafkaTopic;
    private String kafkaGroupId;

    public KafkaConfig() {
        kafkaProps = new Properties();
    }

    public void setKafkaProducer() {
    }
}
