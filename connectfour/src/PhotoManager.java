import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

public class PhotoManager {


    public static BufferedImage downloadImage(String sourceUrl) throws IOException{
        BufferedImage originalImage = null;

            URL imageUrl = new URL(sourceUrl);
            InputStream inputStream = imageUrl.openStream();
            originalImage = ImageIO.read(inputStream);

        return originalImage;
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) throws MalformedURLException {

            //scaling
            int originalWidth = originalImage.getWidth();
            int oringinalHeigt = originalImage.getHeight();
            double scaleX = (double) newWidth / originalWidth;
            double scaleY = (double) newHeight / oringinalHeigt;
            double scale = Math.min(scaleX, scaleY);

            // create Image
        int scaleWidth = (int) (originalWidth * scale);
        int scaleHeight = (int) (oringinalHeigt * scale);

        return new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);
    }

    public static String encodeImage(BufferedImage image){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String convertedImage;

        try {
            ImageIO.write(image, "jpg", outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            convertedImage = Base64.getEncoder().encodeToString(imageBytes);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return convertedImage;
    }

    public static ImageIcon decodeImage(String encodedImage){
        ImageIcon icon = null;
        try{

            byte[] decodedImage = Base64.getDecoder().decode(encodedImage);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedImage);
            BufferedImage image = ImageIO.read(inputStream);
            inputStream.close();

            icon = new ImageIcon(image);

        }catch (Exception e){
            e.printStackTrace();
        }
        return icon;
    }

}
