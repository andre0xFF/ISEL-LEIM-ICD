package domain;

import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import application.Resource;
import application.User;

public class Student implements Resource, User {
    private String id;
    private Integer number;
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

    public Student() {
    }

    public Student(
        String id, 
        Integer number,
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
        this.number = number;
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

    public Integer getNumber() {
        return number;
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

    @JacksonXmlElementWrapper(localName = "languages")
    @JacksonXmlProperty(localName = "language")
    public ArrayList<String> getLanguages() {
        return languages;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
