package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Repository;
import pt.isel.icd.serialization.Serializer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class UserServerRepository implements Repository {
    private final Serializer serializer;
    private final HashMap<String, File> files = new HashMap<>();
    private Users users = new Users();
    private Profiles profiles = new Profiles();

    public UserServerRepository(Serializer existingSerializer) {
        this.serializer = existingSerializer;
    }

    public void addFile(String users, File file) {
        files.put(users, file);
    }

    public void loadUsers() throws IOException {
        File file = files.get("users");

        if (!file.exists()) {
            throw new IllegalStateException("File does not exist");
        }

        users = serializer.deserialize(file, Users.class);
    }

    public void saveUsers(File file) throws IOException {
        serializer.serialize(file, users);
    }

    public void loadProfiles() throws IOException {
        File file = files.get("profiles");

        if (!file.exists()) {
            throw new IllegalStateException("File does not exist");
        }

        profiles = serializer.deserialize(file, Profiles.class);
    }

    public void saveProfiles(File file) throws IOException {
        serializer.serialize(file, profiles);
    }

    public User readUser(String username) {
        return users.read(username);
    }

    public void addUser(User user) {
        users.add(user);

        try {
            saveUsers(files.get("users"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public Profile readProfile(String username) {
        return profiles.read(username);
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);

        try {
            saveProfiles(files.get("profiles"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProfile(Profile profile) {
        profiles.update(profile);

        try {
            saveProfiles(files.get("profiles"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProfile(Profile profile) {
        profiles.remove(profile);
    }
}
