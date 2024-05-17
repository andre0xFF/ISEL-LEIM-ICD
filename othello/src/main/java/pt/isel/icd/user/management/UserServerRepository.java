package pt.isel.icd.user.management;

import pt.isel.icd.database.Database;
import pt.isel.icd.patterns.verticals.Repository;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.Profiles;
import pt.isel.icd.user.logic.User;
import pt.isel.icd.user.logic.Users;

import java.io.File;
import java.util.HashMap;

public class UserServerRepository implements Repository {
    private final HashMap<String, File> files = new HashMap<>();
    private Users users = new Users();
    private Profiles profiles = new Profiles();
    private Database database;

    public UserServerRepository(Database existingDatabase) {
        database = existingDatabase;
    }

    public void loadDatabase() {
        loadUsers();
        loadProfiles();
    }

    public void saveDatabase() {
        saveUsers();
        saveProfiles();
    }

    public void loadUsers() {
        database.load(users.getClass());
    }

    public void saveUsers() {
        database.save(users);
    }


    public void loadProfiles() {
        database.load(profiles.getClass());
    }

    public void saveProfiles() {
        database.save(profiles);
    }

    public void addFile(String users, File file) {
        files.put(users, file);
    }

    public User readUser(String username) {
        return users.read(username);
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public Profile readProfile(User user) {
        return profiles.read(user);
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
        saveProfiles();
    }

    public void updateProfile(Profile profile) {
        profiles.update(profile);
        saveProfiles();
    }

    public void removeProfile(Profile profile) {
        profiles.remove(profile);
    }
}
