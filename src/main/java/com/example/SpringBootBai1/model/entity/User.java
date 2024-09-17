package com.example.SpringBootBai1.model.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class User {
    // uid int auto_increment primary key,
    // name varchar(64),
    // dob date,
    // gender varchar(1),
    // phone varchar(16),
    // email varchar(50),
    // is_active tinyint,
    // website text,
    // username varchar(64),
    // password varchar(128),
    // avatar text,
    // role varchar(1)
    private int uid;
    private String name;
    private Date dob;
    private char gender;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private char role;

    public User(String name, Date dob, char gender, String phone, String email, String username, String password,
            char role) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String name, char gender, String phone, String email, String username, String password, char role) {

        this.name = name;

        this.gender = gender;

        this.phone = phone;

        this.email = email;

        this.username = username;

        this.password = password;

        this.role = role;

    }

    public User(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public User(String email, char gender, String name, String password, String phone, char role, String username) {

        this.email = email;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.username = username;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public char getGender() {
        return this.gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public char getRole() {
        return this.role;
    }

    public void setRole(char role) {
        this.role = role;
    }
    

    public User orElseThrow(Object userNotFound) {
        return null;
    }

    public boolean isVerified() {
        return role != UserRole.UNVERIFIED_USER.getValue() && role != UserRole.UNVERIFIED_CHUSAN.getValue();
    }

    public UserRole getUserRole() {
        return UserRole.fromValue(role);
    }

    @Getter
    public enum UserRole {
    
        ADMIN('A'),
    
        ChuSan('C'),
        UNVERIFIED_CHUSAN('c'),
        BANNED_CHUSAN('p'),
    
        USER('U'),
        UNVERIFIED_USER('u'),
        BANNED_USER('b');
    
        private final char value;
    
        UserRole(char value) {
            this.value = value;
        }
    
        public char getValue() {
            return value;
        }
    
        public static UserRole fromValue(char value) {
            for (UserRole role : UserRole.values()) {
                if (role.getValue() == value) {
                    return role;
                }
            }
            return null;
        }
    }

}
