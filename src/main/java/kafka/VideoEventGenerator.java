package kafka;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Base64;

public class VideoEventGenerator implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(VideoEventGenerator.class.getSimpleName());

    private String cameraId;
    private String url;
    private KafkaProducer<String, String> mProducer;
    private String topic;

    public VideoEventGenerator(String cameraid, String url, KafkaProducer<String,String> producer, String topic) {
        this.cameraId = cameraid;
        this.url = url;
        this.mProducer = producer;
        this.topic = topic;
    }

    static {
        nu.pattern.OpenCV.loadShared();
    }

    @Override
    public void run() {
        logger.info("Processing cameraId "+cameraId);
        try {
            generateEvent(cameraId, url, mProducer, topic);
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void generateEvent(String cameraid, String url,
                               KafkaProducer<String, String> producer, String topic) throws Exception {
        VideoCapture camera = null;
        if(StringUtils.isNumeric(url)) {
            camera = new VideoCapture(Integer.parseInt(url));
        }
        else {
            camera = new VideoCapture(url);
        }

        if(!camera.isOpened()) {
            Thread.sleep(5000);
            if(!camera.isOpened()) {
                throw new Exception("Error while opening camera");
            }
        }
        Mat mat = new Mat();
        Gson gson = new Gson();

        while(camera.read(mat)) {
            Imgproc.resize(mat,mat, new Size(640,480), 0, 0, Imgproc.INTER_CUBIC);
            int cols = mat.cols();
            int rows = mat.rows();
            int type = mat.type();

            byte[] data = new byte[(int) (mat.total() * mat.channels())];
            mat.get(0,0, data);

            String timestamp = new Timestamp(System.currentTimeMillis()).toString();

            JsonObject obj = new JsonObject();
            obj.addProperty("cameraId",cameraid);
            obj.addProperty("rows",rows);
            obj.addProperty("type", type);
            obj.addProperty("data", Base64.getEncoder().encodeToString(data));
            String json = gson.toJson(obj);

            producer.send(new ProducerRecord<String, String>(topic, cameraid, json), new EventGeneratorCallback(cameraid));
            logger.info("Generated events for cameraId "+cameraid);
        }
        camera.release();
        mat.release();
    }

    private class EventGeneratorCallback implements Callback {
        private String camId;

        public EventGeneratorCallback(String cam) {
            super();
            this.camId =cam;
        }

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if(recordMetadata != null) {
                logger.info("camera Id= "+camId+" partition="+recordMetadata.partition());
            }
            if( e != null) {
                e.printStackTrace();
            }
        }
    }
}
