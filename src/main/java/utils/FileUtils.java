package utils;

import kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class.getName());
    public FileUtils() {
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

    public static byte[] extractFileBytes(String fileName) {
        try {
            Path file = Paths.get(fileName);
            byte[] data = Files.readAllBytes(file);
            return data;
        } catch(IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static int saveFileBytes(String filePath, byte[] data) {
        createPath(filePath);
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(data);
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
        return KafkaConstants.PATH_SAVE_IMAGE + '/'+fileName;
    }
}
