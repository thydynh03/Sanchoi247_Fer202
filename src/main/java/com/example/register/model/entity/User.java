package com.example.register.model.entity;

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
    private int uid;
    private String name;
    private Date dob;
    private Character gender;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private UserRole role;

    // Constructor without UID and Avatar
    public User(String name, Date dob, Character gender, String phone, String email, 
                String username, String password, UserRole role) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor with all fields
    public User(int uid, String name, Date dob, char gender, String phone, 
                String email, String username, String password, String avatar, char roleChar) {
        this.uid = uid;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.role = UserRole.fromValue(roleChar);
    }

    public boolean isVerified() {
        return role != UserRole.UNVERIFIED_USER && role != UserRole.UNVERIFIED_CHUSAN;
    }

    public enum UserRole {
        ADMIN('A'),
        CHUSAN('C'),
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
            throw new IllegalArgumentException("Unknown role: " + value);
        }
    }
}