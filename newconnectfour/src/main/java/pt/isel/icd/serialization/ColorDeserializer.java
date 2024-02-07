package pt.isel.icd.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.awt.*;

public class ColorDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<Color> {
    @Override
    public Color deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int red = node.get("red").asInt();
        int green = node.get("green").asInt();
        int blue = node.get("blue").asInt();

        return new Color(red, green, blue);
    }
}
