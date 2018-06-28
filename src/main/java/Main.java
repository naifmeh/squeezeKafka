import kafka.*;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getSimpleName());

    @Option(name="-type",usage="Define a producer or consumer")
    public static String kafkaType;


    @Option(name="-file",usage="File input for producer")
    public static String fileProduce;

    @Option(name="-brokers",usage="Brokers with port separated by comma")
    public static String mBrokers;

    @Option(name="-groupid",usage="Group ID to which consumer should belong")
    public static String mGroupId;

    @Argument
    private static List<String> arguments = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        logger.info("Starting program...");

        new Main().doMain(args);

        if(kafkaType == null) return;

        if(kafkaType.equals(KafkaConstants.KAFKA_IMAGE_PRODUCER_TYPE)) {
            if(mBrokers == null || fileProduce == null) return;

            ImageProducer imageProducer = new ImageProducer(mBrokers);
            imageProducer.sendImage(fileProduce, FileUtils.extractImageBytes(fileProduce));
            return;
        } else if(kafkaType.equals((KafkaConstants.KAFKA_IMAGE_CONSUMER_TYPE))) {
            if(mBrokers == null) return;
            ImageConsumer consumer = new ImageConsumer(mBrokers,mGroupId);
            while(true) {
                consumer.consumeImage();
            }
        } else if(kafkaType.equals(KafkaConstants.KAFKA_CLASSIFIER_PRODUCER_TYPE)) {
            if(mBrokers == null) return;
            ClassifierProducer producer = new ClassifierProducer(mBrokers);
            String[] fileSplit = FileUtils.splitFilePath(fileProduce);
            producer.sendClassifier(fileSplit[fileSplit.length-1],FileUtils.extractFileBytes(fileProduce));
            return;
        } else if(kafkaType.equals(KafkaConstants.KAFKA_CLASSIFIER_CONSUMER_TYPE)) {
            if(mBrokers == null) return;
            ClassifierConsumer consumer;
            if(mGroupId == null) consumer = new ClassifierConsumer(mBrokers);
            else consumer = new ClassifierConsumer(mBrokers,mGroupId);

            while(true) {
                consumer.consumeClassifier();
            }
        } else if(kafkaType.equals(KafkaConstants.KAFKA_VIDEO_PRODUCER_TYPE)) {
            if(mBrokers == null) return;
            VideoProducer producer = new VideoProducer(mBrokers,KafkaConstants.VIDEO_TOPIC_NAME);
            producer.generateVideoEvent("testCam","0");
        }

        return;
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser =  new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch( CmdLineException e) {
            e.printStackTrace();
            return;
        }
        for(String s :arguments) {
            System.out.println(arguments);
        }
    }
}
