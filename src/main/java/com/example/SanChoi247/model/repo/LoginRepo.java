package com.example.SanChoi247.model.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.example.SanChoi247.model.entity.User;

@Repository
public class LoginRepo {
    static Scanner scanner = new Scanner(System.in);
    public User checkLogin(String username, String password) throws Exception{
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where username = ? and password =?");// ? la 1 parameter de biet vi tri cua han o dau de truyen vao
        ps.setString(1, username);
        ps.setString(2, password);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int uid = rs.getInt("uid");
        String name = rs.getString("name");
        Date dob = rs.getDate("dob");
        char gender = rs.getString("gender").charAt(0);
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String username1 = rs.getString("username");
        String password1 = rs.getString("password");
        String avatar = rs.getString("avatar");
        char role = rs.getString("role").charAt(0);
        User user = new User(uid, name, dob, gender, phone, email, username1, password1, avatar, role);
        return user;
    }
}
