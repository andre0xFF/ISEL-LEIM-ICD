package pt.isel.icd.user.management;

import pt.isel.icd.patterns.verticals.Repository;
import pt.isel.icd.serialization.Serializer;

import java.io.File;
import java.io.IOException;

public class UserServerRepository implements Repository {
    private final Serializer serializer;
    private Users users = new Users();
    private File file;

    public UserServerRepository(Serializer existingSerializer) {
        this.serializer = existingSerializer;
    }

    public void load(File existingFile) throws IOException {
        file = existingFile;

        if (!existingFile.exists()) {
            throw new IllegalStateException("File does not exist");
        }

        users = serializer.deserialize(existingFile, Users.class);
    }

    public void save(File file) throws IOException {
        serializer.serialize(file, users);
    }

    public User findUserbyUsername(String username) {
        return users.user(username);
    }

    public User addUser(String username, String password) {
        User user = users.add(username, password);

        try {
            save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public User removeUser(String username) {
        return users.remove(username);
    }
}
