package pt.isel.icd.user.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pt.isel.icd.database.XmlFileStore;
import pt.isel.icd.user.logic.Profile;
import pt.isel.icd.user.logic.User;

public class UserServerRepository {

    private final XmlFileStore xmlFileStore;
    private final List<User> users = new ArrayList<>();
    private final List<Profile> profiles = new ArrayList<>();

    public UserServerRepository(XmlFileStore xmlFileStore) {
        this.xmlFileStore = xmlFileStore;
    }

    public void loadUsers() {
        users.clear();
        List<Map<String, String>> data = xmlFileStore.loadUsers();
        for (Map<String, String> entry : data) {
            users.add(new User(entry.get("username"), entry.get("password")));
        }
    }

    public void saveUsers() {
        List<Map<String, String>> data = new ArrayList<>();
        for (User user : users) {
            Map<String, String> entry = new HashMap<>();
            entry.put("username", user.username());
            entry.put("password", user.password());
            data.add(entry);
        }
        xmlFileStore.saveUsers(data);
    }

    public void loadProfiles() {
        profiles.clear();
        List<Map<String, String>> data = xmlFileStore.loadProfiles();
        for (Map<String, String> entry : data) {
            // Perfis antigos (sem os campos novos) recebem valores por omissao.
            profiles.add(
                new Profile(
                    entry.get("username"),
                    orDefault(entry.get("fullName"), ""),
                    orDefault(entry.get("nationality"), ""),
                    parseInt(entry.get("age"), 0),
                    orDefault(entry.get("photo"), ""),
                    orDefault(
                        entry.get("preferredColor"),
                        Profile.DEFAULT_COLOR
                    ),
                    parseInt(entry.get("wins"), 0),
                    parseInt(entry.get("losses"), 0),
                    parseInt(entry.get("totalGames"), 0),
                    parseLong(entry.get("totalTimeMillis"), 0L)
                )
            );
        }
    }

    public void saveProfiles() {
        List<Map<String, String>> data = new ArrayList<>();
        for (Profile profile : profiles) {
            Map<String, String> entry = new HashMap<>();
            entry.put("username", profile.username());
            entry.put("fullName", orDefault(profile.fullName(), ""));
            entry.put("nationality", orDefault(profile.nationality(), ""));
            entry.put("age", String.valueOf(profile.age()));
            entry.put("photo", orDefault(profile.photo(), ""));
            entry.put(
                "preferredColor",
                orDefault(profile.preferredColor(), Profile.DEFAULT_COLOR)
            );
            entry.put("wins", String.valueOf(profile.wins()));
            entry.put("losses", String.valueOf(profile.losses()));
            entry.put("totalGames", String.valueOf(profile.totalGames()));
            entry.put(
                "totalTimeMillis",
                String.valueOf(profile.totalTimeMillis())
            );
            data.add(entry);
        }
        xmlFileStore.saveProfiles(data);
    }

    private static String orDefault(String value, String fallback) {
        return value != null ? value : fallback;
    }

    private static int parseInt(String value, int fallback) {
        try {
            return value != null ? Integer.parseInt(value.trim()) : fallback;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static long parseLong(String value, long fallback) {
        try {
            return value != null ? Long.parseLong(value.trim()) : fallback;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    public User readUser(String username) {
        return users
            .stream()
            .filter(u -> u.username().equals(username))
            .findFirst()
            .orElse(null);
    }

    public void addUser(User user) {
        if (readUser(user.username()) != null) {
            throw new IllegalArgumentException("User already exists");
        }
        users.add(user);
        saveUsers();
    }

    public void removeUser(User user) {
        users.removeIf(u -> u.username().equals(user.username()));
        saveUsers();
    }

    public Profile readProfile(String username) {
        return profiles
            .stream()
            .filter(p -> p.username().equals(username))
            .findFirst()
            .orElse(null);
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
        saveProfiles();
    }

    public void updateProfile(Profile profile) {
        profiles.removeIf(p -> p.username().equals(profile.username()));
        profiles.add(profile);
        saveProfiles();
    }

    public void removeProfile(Profile profile) {
        profiles.removeIf(p -> p.username().equals(profile.username()));
        saveProfiles();
    }
}
