package pt.isel.icd.user.logic;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import pt.isel.icd.database.Entity;

import java.util.ArrayList;

@JacksonXmlRootElement(localName = "profiles")
public class Profiles implements Entity {

    @JacksonXmlProperty(localName = "profile")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final ArrayList<Profile> profiles = new ArrayList<>();

    public Profile read(User user) {
        return profiles.stream()
                .filter(profile -> profile.username().equals(user.username()))
                .findFirst()
                .orElse(null);
    }

    public void add(Profile profile) {
        profiles.add(profile);
    }

    public void remove(Profile profile) {
        Profile existingProfile = profiles.stream()
                .filter(p -> p.username().equals(profile.username()))
                .findFirst()
                .orElse(null);

        if (existingProfile == null) {
            throw new IllegalArgumentException("Profile does not exist");
        }

        profiles.remove(existingProfile);
    }

    public void update(Profile profile) {
        remove(profile);
        add(profile);
    }

    @Override
    public String name() {
        return "user/management/Profiles";
    }
}
