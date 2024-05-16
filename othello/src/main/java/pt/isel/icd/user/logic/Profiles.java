package pt.isel.icd.user.management;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.patterns.verticals.Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@JacksonXmlRootElement(localName = "profiles")
public class Profiles implements Entity {

    @JacksonXmlProperty(localName = "profile")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final ArrayList<Profile> profiles = new ArrayList<>();

    public Profile read(String username) {
        return profiles.stream()
                .filter(profile -> profile.username().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void add(Profile profile) {
        profiles.add(profile);
    }

    public void update(Profile profile) {
        Profile existingProfile = read(profile.username());

        if (existingProfile == null) {
            throw new IllegalArgumentException("Profile does not exist");
        }

        profiles.remove(existingProfile);
        profiles.add(profile);
    }

    public void remove(Profile profile) {
        Profile existingProfile = read(profile.username());

        if (existingProfile == null) {
            throw new IllegalArgumentException("Profile does not exist");
        }

        profiles.remove(existingProfile);
    }
}
