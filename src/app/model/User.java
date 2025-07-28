package app.model;

import java.sql.*;
import app.utility.DatabaseUtility;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private int id;
    private String username;
    private String fullname;
    private String phoneNumber;
    private String email;
    private String gender;
    private Date dateOfBirth;
    private String password;
    private String role;
    private Timestamp datetimeJoined;
    private Timestamp datetimeLastUpdated;

    public User(int id, String username, String fullname, String phoneNumber, String email, String gender, Date dateOfBirth, String password, String role, Timestamp datetimeJoined, Timestamp datetimeLastUpdated) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.role = role;
        this.datetimeJoined = datetimeJoined;
        this.datetimeLastUpdated = datetimeLastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getDatetimeJoined() {
        return datetimeJoined;
    }

    public void setDatetimeJoined(Timestamp datetimeJoined) {
        this.datetimeJoined = datetimeJoined;
    }

    public Timestamp getDatetimeLastUpdated() {
        return datetimeLastUpdated;
    }

    public void setDatetimeLastUpdated(Timestamp datetimeLastUpdated) {
        this.datetimeLastUpdated = datetimeLastUpdated;
    }
    
    
}

