package pt.isel.icd.user.management;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.patterns.verticals.Entity;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;

@JacksonXmlRootElement(localName = "profile")
public record Profile(
        @JacksonXmlProperty(localName = "username")
        String username,

        @JacksonXmlProperty(localName = "nationality")
        String nationality,

        @JacksonXmlProperty(localName = "age")
        Integer age,

// TODO: Implement BufferedImage serialization
//        @JacksonXmlProperty(localName = "photo")
//        BufferedImage photo,

        @JacksonXmlProperty(localName = "wins")
        Integer wins,

        @JacksonXmlProperty(localName = "losses")
        Integer losses,

        @JacksonXmlProperty(localName = "timeSpentOnEachGame")
        Map<String, Long> timeSpentOnEachGame
) implements Entity {
    public Profile {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be a positive number");
        }
        if (wins < 0) {
            throw new IllegalArgumentException("Wins must be a positive number");
        }
        if (losses < 0) {
            throw new IllegalArgumentException("Losses must be a positive number");
        }
        if (timeSpentOnEachGame == null) {
            throw new IllegalArgumentException("Time spent on each game must be a non-null map");
        }
    }
}
