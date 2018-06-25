package utils;

import kafka.ImageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class.getName());
    public ImageUtils() {
    }

    public static byte[] extractImageBytes(String imageName) {
        try {
            BufferedImage bufferedImage = ImageIO.read( new File(imageName));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            ImageIO.write(bufferedImage, "jpg",bos);
            byte[] data = bos.toByteArray();

            return data;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static int saveImageBytes(String filePath, byte[] image) {
        createPath(filePath);
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(image);
            return 0;
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return -1;
    }

    public static boolean createPath(String filePath) {
        String[] cutFilePath = splitFilePath(filePath);
        String directoryPath= cutFilePath[0];
        for(int i=1;i<cutFilePath.length-1;i++) {
            directoryPath += ('/'+cutFilePath[i]);
        }

        System.out.println(directoryPath);
        return new File(directoryPath).mkdirs();
    }

    public static String[] splitFilePath(String filePath) {
        String[] cutFilePath = filePath.split("/");
        return cutFilePath;
    }

    public static String appendImagePath(String fileName) {
        return ImageConsumer.PATH_SAVE_IMAGE + '/'+fileName;
    }
}
