package pt.isel.icd.database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pt.isel.icd.serialization.XmlHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DOM-based XML file store for users and profiles.
 * No data-binding (Jackson/JAXB) is used.
 */
public class XmlFileStore {

    private String fileStorePath;

    public void setFileStorePath(String path) {
        this.fileStorePath = path;
    }

    public String getFileStorePath() {
        return fileStorePath;
    }

    /**
     * Loads a list of users from Users.xml.
     * Expected format:
     * <users>
     *   <user>
     *     <username>...</username>
     *     <password>...</password>
     *   </user>
     * </users>
     */
    public List<Map<String, String>> loadUsers() {
        File file = new File(fileStorePath + "/user/management/Users.xml");
        List<Map<String, String>> users = new ArrayList<>();

        if (!file.exists()) return users;

        Document doc = XmlHelper.parse(file);
        NodeList userNodes = doc.getElementsByTagName("user");

        for (int i = 0; i < userNodes.getLength(); i++) {
            Element userEl = (Element) userNodes.item(i);
            Map<String, String> user = new HashMap<>();
            user.put("username", XmlHelper.getChildText(userEl, "username"));
            user.put("password", XmlHelper.getChildText(userEl, "password"));
            users.add(user);
        }

        return users;
    }

    /**
     * Saves a list of users to Users.xml.
     */
    public void saveUsers(List<Map<String, String>> users) {
        Document doc = XmlHelper.createDocument();
        Element root = doc.createElement("users");
        doc.appendChild(root);

        for (Map<String, String> user : users) {
            Element userEl = doc.createElement("user");
            root.appendChild(userEl);
            XmlHelper.addChildElement(doc, userEl, "username", user.get("username"));
            XmlHelper.addChildElement(doc, userEl, "password", user.get("password"));
        }

        File file = new File(fileStorePath + "/user/management/Users.xml");
        file.getParentFile().mkdirs();
        XmlHelper.serialize(doc, file);
    }

    /**
     * Loads a list of profiles from Profiles.xml.
     * Expected format:
     * <profiles>
     *   <profile>
     *     <username>...</username>
     *     <nationality>...</nationality>
     *     <age>...</age>
     *     <photo>...</photo>
     *     <wins>...</wins>
     *     <losses>...</losses>
     *     <gamesPlayed>
     *       <game>
     *         <gameId>...</gameId>
     *         <timeSpent>...</timeSpent>
     *       </game>
     *     </gamesPlayed>
     *   </profile>
     * </profiles>
     */
    public List<Map<String, String>> loadProfiles() {
        File file = new File(fileStorePath + "/user/management/Profiles.xml");
        List<Map<String, String>> profiles = new ArrayList<>();

        if (!file.exists()) return profiles;

        Document doc = XmlHelper.parse(file);
        NodeList profileNodes = doc.getElementsByTagName("profile");

        for (int i = 0; i < profileNodes.getLength(); i++) {
            Element profileEl = (Element) profileNodes.item(i);
            Map<String, String> profile = new HashMap<>();
            profile.put("username", XmlHelper.getChildText(profileEl, "username"));
            profile.put("nationality", XmlHelper.getChildText(profileEl, "nationality"));
            profile.put("age", XmlHelper.getChildText(profileEl, "age"));
            profile.put("photo", XmlHelper.getChildText(profileEl, "photo"));
            profile.put("wins", XmlHelper.getChildText(profileEl, "wins"));
            profile.put("losses", XmlHelper.getChildText(profileEl, "losses"));
            profiles.add(profile);
        }

        return profiles;
    }

    /**
     * Saves a list of profiles to Profiles.xml.
     */
    public void saveProfiles(List<Map<String, String>> profiles) {
        Document doc = XmlHelper.createDocument();
        Element root = doc.createElement("profiles");
        doc.appendChild(root);

        for (Map<String, String> profile : profiles) {
            Element profileEl = doc.createElement("profile");
            root.appendChild(profileEl);
            XmlHelper.addChildElement(doc, profileEl, "username", profile.get("username"));
            XmlHelper.addChildElement(doc, profileEl, "nationality", profile.get("nationality"));
            XmlHelper.addChildElement(doc, profileEl, "age", profile.get("age"));
            XmlHelper.addChildElement(doc, profileEl, "photo", profile.get("photo"));
            XmlHelper.addChildElement(doc, profileEl, "wins", profile.get("wins"));
            XmlHelper.addChildElement(doc, profileEl, "losses", profile.get("losses"));
        }

        File file = new File(fileStorePath + "/user/management/Profiles.xml");
        file.getParentFile().mkdirs();
        XmlHelper.serialize(doc, file);
    }
}
