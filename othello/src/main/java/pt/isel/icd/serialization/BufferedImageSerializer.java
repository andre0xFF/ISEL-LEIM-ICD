package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BufferedImageSerializer extends JsonSerializer<BufferedImage> {
    @Override
    public void serialize(BufferedImage image, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (image != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            gen.writeBinary(imageBytes);
        } else {
            gen.writeNull();
        }
    }
}
