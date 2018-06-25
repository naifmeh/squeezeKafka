import kafka.ImageConsumer;
import kafka.ImageProducer;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ImageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getSimpleName());

    @Option(name="-type",usage="Define a producer or consumer")
    public static String kafkaType;
    public static final String KAFKA_PRODUCER_TYPE = "producer";
    public static final String KAFKA_CONSUMER_TYPE = "consumer";

    @Option(name="-file",usage="File input for producer")
    public static String fileProduce;

    @Option(name="-brokers",usage="Brokers with port separated by comma")
    public static String mBrokers;

    @Option(name="-groupid",usage="Group ID to which consumer should belong")
    public static String mGroupId;

    @Argument
    private static List<String> arguments = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        logger.info("Starting program...");

        new Main().doMain(args);
        System.out.println("------------"+kafkaType+"-----------");
        if(kafkaType != null && kafkaType.equals(KAFKA_PRODUCER_TYPE)) {
            if(mBrokers == null || fileProduce == null) return;

            ImageProducer imageProducer = new ImageProducer(mBrokers);
            imageProducer.sendImage(fileProduce, ImageUtils.extractImageBytes(fileProduce));
            return;
        }

        if(kafkaType != null && kafkaType.equals((KAFKA_CONSUMER_TYPE))) {
            if(mBrokers == null) return;
            ImageConsumer consumer = new ImageConsumer(mBrokers,mGroupId);
            while(true) {
                consumer.consumeImage();
            }
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
