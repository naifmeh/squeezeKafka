package kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassifierProducer {
    private static Logger logger = LoggerFactory.getLogger(ClassifierProducer.class.getSimpleName());

    public static final String CLASSIFIER_TOPIC_NAME = "classifier-pkl-topic";
    public ClassifierProducer() {
    }
}
