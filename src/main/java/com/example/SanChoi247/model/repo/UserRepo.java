package com.example.SanChoi247.model.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Repository;

import com.example.SanChoi247.model.dto.SignUpRequest;
import com.example.SanChoi247.model.entity.User;

@Repository
public class UserRepo {
    private static final String SIGN_UP_STATEMENT = "INSERT INTO users (uid, status) VALUES (?, ?);";
    public ArrayList<User> getAllUser() throws Exception {
        ArrayList<User> UserList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username, Baseconnection.password);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM users");
        
        while (rs.next()) {
            int uid = rs.getInt("uid");
            String name = rs.getString("name");
            Date dob = rs.getDate("dob");
    
            // Kiểm tra gender
            String genderString = rs.getString("gender");
            char gender = (genderString != null && !genderString.isEmpty()) ? genderString.charAt(0) : 'M'; // Giá trị mặc định nếu null
    
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String avatar = rs.getString("avatar");
            String ten_san = rs.getString("ten_san");
            String address = rs.getString("address");
            String img_san1 = rs.getString("img_san1");
            String img_san2 = rs.getString("img_san2");
            String img_san3 = rs.getString("img_san3");
            String img_san4 = rs.getString("img_san4");
            String img_san5 = rs.getString("img_san5");
            int status = rs.getInt("status");
    
            // Kiểm tra role
            String roleString = rs.getString("role");
            char role = (roleString != null && !roleString.isEmpty()) ? roleString.charAt(0) : 'U'; // Giá trị mặc định nếu null
    
            User user = new User(uid, name, dob, gender, phone, email, username, password, avatar, ten_san, address,
                    img_san1, img_san2, img_san3, img_san4, img_san5, status, role);
            
            UserList.add(user);
        }
    
        // Đừng quên đóng kết nối
        rs.close();
        stm.close();
        con.close();
    
        return UserList;
    }
    

    public User getUserById(int id) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where uid = ?");
        ps.setInt(1, id);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int uid = rs.getInt("uid");
        String name = rs.getString("name");
        Date dob = rs.getDate("dob");
        char gender = rs.getString("gender").charAt(0);
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String avatar = rs.getString("avatar");
        String ten_san = rs.getString("ten_san");
        String address = rs.getString("address");
        String img_san1 = rs.getString("img_san1");
        String img_san2 = rs.getString("img_san2");
        String img_san3 = rs.getString("img_san3");
        String img_san4 = rs.getString("img_san4");
        String img_san5 = rs.getString("img_san5");
        int status = rs.getInt("status");
        char role = rs.getString("role").charAt(0);
        User user = new User(uid, name, dob, gender, phone, email, username, password, avatar, ten_san, address,
                img_san1, img_san2, img_san3, img_san4, img_san5, status, role);
        return user;
    }

    public User getUserByUsername(String username) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where username = ?");
        ps.setString(1, username);
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
        String password = rs.getString("password");
        String avatar = rs.getString("avatar");
        String ten_san = rs.getString("ten_san");
        String address = rs.getString("address");
        String img_san1 = rs.getString("img_san1");
        String img_san2 = rs.getString("img_san2");
        String img_san3 = rs.getString("img_san3");
        String img_san4 = rs.getString("img_san4");
        String img_san5 = rs.getString("img_san5");
        int status = rs.getInt("status");
        char role = rs.getString("role").charAt(0);
        User user = new User(uid, name, dob, gender, phone, email, username1, password, avatar, ten_san, address,
                img_san1, img_san2, img_san3, img_san4, img_san5, status, role);
        return user;
    }

    public User getUserByEmail(String email) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where email = ?");
        ps.setString(1, email);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int uid = rs.getInt("uid");
        String name = rs.getString("name");
        Date dob = rs.getDate("dob");
        char gender = rs.getString("gender").charAt(0);
        String phone = rs.getString("phone");
        String email1 = rs.getString("email");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String avatar = rs.getString("avatar");
        String ten_san = rs.getString("ten_san");
        String address = rs.getString("address");
        String img_san1 = rs.getString("img_san1");
        String img_san2 = rs.getString("img_san2");
        String img_san3 = rs.getString("img_san3");
        String img_san4 = rs.getString("img_san4");
        String img_san5 = rs.getString("img_san5");
        int status = rs.getInt("status");
        char role = rs.getString("role").charAt(0);
        User user = new User(uid, name, dob, gender, phone, email1, username, password, avatar, ten_san, address,
                img_san1, img_san2, img_san3, img_san4, img_san5, status, role);
        return user;
    }

    public ArrayList<User> getAllUserByRole(char role) throws Exception {
        ArrayList<User> UserList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where role = ?");
        ps.setString(1, String.valueOf(role));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int uid = rs.getInt("uid");
            String name = rs.getString("name");
            Date dob = rs.getDate("dob");
            char gender = rs.getString("gender").charAt(0);
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String avatar = rs.getString("avatar");
            String ten_san = rs.getString("ten_san");
            String address = rs.getString("address");
            String img_san1 = rs.getString("img_san1");
            String img_san2 = rs.getString("img_san2");
            String img_san3 = rs.getString("img_san3");
            String img_san4 = rs.getString("img_san4");
            String img_san5 = rs.getString("img_san5");
            int status = rs.getInt("status");
            char role1 = rs.getString("role").charAt(0);
            User user = new User(uid, name, dob, gender, phone, email, username, password, avatar, ten_san, address,
                    img_san1, img_san2, img_san3, img_san4, img_san5, status, role1);
            UserList.add(user);
        }
        return UserList;
    }



    public void addNewUser(User user) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        
        // Mã hóa mật khẩu
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
    
        // Câu lệnh SQL với thêm cột role
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO users (name, dob, gender, phone, email, username, password, avatar, ten_san, address, img_san1, img_san2, img_san3, img_san4, img_san5, status, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    
        ps.setString(1, user.getName());
        ps.setDate(2, user.getDob());
        ps.setString(3, String.valueOf(user.getGender()));
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getUsername());
        ps.setString(7, encodedPassword); // Lưu mật khẩu đã mã hóa
        ps.setString(8, user.getAvatar());
        ps.setString(9, user.getTen_san());
        ps.setString(10, user.getAddress());
        ps.setString(11, user.getImg_san1());
        ps.setString(12, user.getImg_san2());
        ps.setString(13, user.getImg_san3());
        ps.setString(14, user.getImg_san4());
        ps.setString(15, user.getImg_san5());
    
        // Gán giá trị 'U' cho cột role
        ps.setString(16, "0");
        ps.setString(17, "U");
    
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    
    public void updateUserById(User user) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update users set name = ?, dob = ?, gender = ?, phone = ?, email = ?, username = ?, password = ?, avatar = ?, ten_san = ?, address = ?, img_san1 = ?, img_san2 = ?, img_san3 = ?, img_san4 = ?, img_san5 =?, status = ? , role = ? where uid = ?");
        ps.setString(1, user.getName());
        ps.setDate(2, user.getDob());
        ps.setString(3, String.valueOf(user.getGender()));
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getUsername());
        ps.setString(7, user.getPassword());
        ps.setString(8, user.getAvatar());
        ps.setString(9, user.getTen_san());
        ps.setString(10, user.getAddress());
        ps.setString(11, user.getImg_san1());
        ps.setString(12, user.getImg_san2());
        ps.setString(13, user.getImg_san3());
        ps.setString(14, user.getImg_san4());
        ps.setString(15, user.getImg_san5());
        ps.setInt(16, user.getStatus());
        ps.setString(17, String.valueOf(user.getRole()));
        ps.setInt(18, user.getUid());
        ps.executeUpdate();
        ps.close();
    }

    public void editAvatarUser(String avatar, int id) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("UPDATE users SET avatar = ? WHERE uid = ?");
        ps.setString(1, avatar);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
    }

    public void editProfile(String name, Date dob, char gender, String phone, String email, int uid)
            throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "UPDATE users SET name = ?, dob= ?, gender = ?, phone = ?, email = ? WHERE uid = ?");
        ps.setString(1, name);
        ps.setDate(2, dob);
        ps.setString(3, String.valueOf(gender));
        ps.setString(4, phone);
        ps.setString(5, email);
        ps.setInt(6, uid);
        ps.executeUpdate();
        ps.close();
    }

    public void save(User user) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update into users(name, dob, gender, phone, email, username, password, avatar, ten_san, address, img_san1, img_san2, img_san3, img_san4, img_san5, status, role) values(?,?,?,?,?,? ?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, user.getName());
        ps.setDate(2, user.getDob());
        ps.setString(3, String.valueOf(user.getGender()));
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getUsername());
        ps.setString(7, user.getPassword());
        ps.setString(8, user.getAvatar());
        ps.setString(9, user.getTen_san());
        ps.setString(10, user.getAddress());
        ps.setString(11, user.getImg_san1());
        ps.setString(12, user.getImg_san2());
        ps.setString(13, user.getImg_san3());
        ps.setString(14, user.getImg_san4());
        ps.setString(15, user.getImg_san5());
        ps.setInt(16, user.getStatus());
        ps.setString(17, String.valueOf(user.getRole()));
        ps.executeUpdate();
        ps.close();
    }                          
    public void updatePassword(int uid, String newPassword) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE uid = ?");
        ps.setString(1, newPassword);
        ps.setInt(2, uid);
        ps.executeUpdate();
        ps.close();
    }

    public boolean checkPassword(int uid, String oldPassword) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("SELECT password FROM users WHERE uid = ?");
        ps.setInt(1, uid);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String existingPassword = rs.getString("password");
            return existingPassword.equals(oldPassword);
        }
        rs.close();
        ps.close();
        return false;
    }

    public boolean existsByUsername(String username) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("SELECT 1 FROM users WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        con.close();
        return exists;
    }

    public void saveNew(User user) throws Exception {
        if (existsByUsername(user.getUsername())) {
            throw new Exception("Username already exists");
        }
        if (existsByEmail(user.getEmail())) {
            throw new Exception("Email already exists");
        }
        if (user.getPhone() != null && existsByPhone(user.getPhone())) {
            throw new Exception("Phone number already exists");
        }

        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO User (name,dob,gender,phone,email,username,password,avatar,role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getName());
        ps.setDate(2, user.getDob());
        ps.setString(3, String.valueOf(user.getGender()));
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getUsername());
        ps.setString(7, user.getPassword());
        ps.setString(8, user.getAvatar());
        ps.setString(9, String.valueOf(user.getRole()));
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int uid = 0;
        if (rs.next()) {
            uid = rs.getInt(1);
            user.setUid(uid);
        }
        ps.close();
        con.close();
    }

    public void saveOnSignup(User user) throws Exception {
        Class.forName(Baseconnection.nameClass);
        try (Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
                PreparedStatement ps = con.prepareStatement(SIGN_UP_STATEMENT)) {
            ps.setInt(1, user.getUid());
            ps.setInt(2, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle exceptions
            e.printStackTrace();
            throw e; // Rethrow the exception to notify the caller
        }
    }

    public boolean isValidPhone(String phone) {
        // Định dạng số điện thoại Việt Nam
        String phoneNumberPattern = "^(\\+84|0)\\d{9,10}$";
        Pattern pattern = Pattern.compile(phoneNumberPattern);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
    
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
    
        if (!hasUpperCase) {
            System.out.println("Password must contain at least one uppercase letter.");
        }
        if (!hasLowerCase) {
            System.out.println("Password must contain at least one lowercase letter.");
        }
        if (!hasDigit) {
            System.out.println("Password must contain at least one digit.");
        }
        if (!hasSpecialChar) {
            System.out.println("Password must contain at least one special character.");
        }
    
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }
    
    public String isPasswordMatching(SignUpRequest signUpRequest) {
        String password = signUpRequest.getPassword();
        String confirmPassword = signUpRequest.getConfirmPassword();
    
        if (password == null || confirmPassword == null) {
            return "Password or Confirm Password cannot be null."; // Handle null case
        }
    
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match."; // Error message if passwords do not match
        }
    
        return ""; // Return an empty string if passwords match
    }
    
    
    public boolean existsByEmail(String email) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("SELECT 1 FROM users WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        con.close();
        return exists;
    }

    public boolean existsByPhone(String phone) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("SELECT 1 FROM users WHERE phone = ?");
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();
        rs.close();
        ps.close();
        con.close();
        return exists;
    }
}