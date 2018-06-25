package utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaConfig {

    private Properties kafkaProps;
    private String clientId = "fdfdrfg";
    private String kafkaBrokers;
    private String kafkaTopic;
    private String kafkaGroupId;

    public KafkaConfig(String brokers, String topics) {
        kafkaProps = new Properties();
        kafkaBrokers = brokers;
        kafkaTopic = topics;

    }

    public KafkaConfig(String brokers,String topics, String groupId) {
        this(brokers,topics);
        kafkaGroupId = groupId;
    }

    public KafkaProducer<String, byte[]> setKafkaProducer() {
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaBrokers);
        kafkaProps.put(ProducerConfig.ACKS_CONFIG,"0");
        kafkaProps.put(ProducerConfig.RETRIES_CONFIG,"3");
        kafkaProps.put(ProducerConfig.CLIENT_ID_CONFIG,clientId);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                ByteArraySerializer.class.getName());

        return new KafkaProducer<>(kafkaProps);
    }

    public KafkaConsumer<String, byte[]> setKafkaConsumer() {
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaBrokers);
        kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG,kafkaGroupId);
        kafkaProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        kafkaProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        kafkaProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,ByteArrayDeserializer.class.getName());

        return new KafkaConsumer<>(kafkaProps);
    }

    public Properties getKafkaProps() {
        return kafkaProps;
    }

    public void setKafkaProps(Properties kafkaProps) {
        this.kafkaProps = kafkaProps;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getKafkaBrokers() {
        return kafkaBrokers;
    }

    public void setKafkaBrokers(String kafkaBrokers) {
        this.kafkaBrokers = kafkaBrokers;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public String getKafkaGroupId() {
        return kafkaGroupId;
    }

    public void setKafkaGroupId(String kafkaGroupId) {
        this.kafkaGroupId = kafkaGroupId;
    }
}
