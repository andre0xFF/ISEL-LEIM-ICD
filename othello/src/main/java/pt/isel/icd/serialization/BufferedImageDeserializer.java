package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedImageDeserializer extends JsonDeserializer<BufferedImage> {
    @Override
    public BufferedImage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        byte[] imageBytes = p.getBinaryValue();
        if (imageBytes != null) {
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } else {
            return null;
        }
    }
}
