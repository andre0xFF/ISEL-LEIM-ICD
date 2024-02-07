package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.*;

class ColorSerializer extends JsonSerializer<Color> {
    @Override
        public void serialize(Color color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws java.io.IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("red", color.getRed());
        jsonGenerator.writeNumberField("green", color.getGreen());
        jsonGenerator.writeNumberField("blue", color.getBlue());
        jsonGenerator.writeEndObject();
    }
}
