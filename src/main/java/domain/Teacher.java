package domain;

import application.Resource;
import application.User;

import java.util.ArrayList;

public class Teacher implements Resource, User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String nationality;
    private String citizenship;
    private String gender;
    private String address;
    private String phoneNumber;
    private ArrayList<String> languages = new ArrayList<>();

    public Teacher() {
    }

    public Teacher(
        String id,
        String firstName,
        String lastName,
        String email,
        String username,
        String password,
        String nationality,
        String citizenship,
        String gender,
        String address,
        String phoneNumber,
        ArrayList<String> languages
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.nationality = nationality;
        this.citizenship = citizenship;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.languages = languages;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
