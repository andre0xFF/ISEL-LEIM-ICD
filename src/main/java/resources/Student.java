package resources;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import application.models.Role;
import application.models.User;

public class Student implements User {
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
    private ArrayList<Role> roles = new ArrayList<>();
    
    @JsonCreator
    public Student(
        @JsonProperty(value = "number") Integer number,
        @JsonProperty(value = "firstName") String firstName,
        @JsonProperty(value = "lastName") String lastName,
        @JsonProperty(value = "email") String email,
        @JsonProperty(value = "username") String username,
        @JsonProperty(value = "password") String password,
        @JsonProperty(value = "nationality") String nationality,
        @JsonProperty(value = "citizenship") String citizenship,
        @JsonProperty(value = "gender") String gender,
        @JsonProperty(value = "address") String address,
        @JsonProperty(value = "phoneNumber") String phoneNumber,
        @JsonProperty(value = "languages") ArrayList<String> languages
    ) {
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
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

    // @JacksonXmlElementWrapper(localName = "languages")
    // @JacksonXmlProperty(localName = "language")
    public ArrayList<String> getLanguages() {
        return languages;
    }

    @Override
    public ArrayList<Role> getRoles() {
        return this.roles;
    }
}
