package com.example.SanChoi247.model.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.SanChoi247.model.entity.LoaiSan;
import com.example.SanChoi247.model.entity.San;
import com.example.SanChoi247.model.entity.User;

@Repository
public class SanRepo {
    @Autowired
    LoaiSanRepo loaiSanRepo;
    @Autowired
    UserRepo userRepo;
    public ArrayList<San> getAllSan() throws Exception {
        ArrayList<San> sanList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username, Baseconnection.password);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM san");
        while (rs.next()) {
            int san_id = rs.getInt("san_id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String location = rs.getString("location");
            int loai_san_id = rs.getInt("loai_san_id");
            LoaiSan loaiSan = loaiSanRepo.getLoaiSanById(loai_san_id);
            String vi_tri_san = rs.getString("vi_tri_san");
            String size = rs.getString("size");
            int uid = rs.getInt("uid");
            User user = userRepo.getUserById(uid);
            String poster = rs.getString("poster");
            String banner = rs.getString("banner");
            int is_approve = rs.getInt("is_approve");
            San san = new San(loai_san_id, name, description, location, loaiSan, vi_tri_san, size, user, poster, banner, is_approve, is_approve);
            sanList.add(san);
        }
        rs.close();
        stm.close();
        con.close();
        return sanList;
    }

    public void addNewSan(San san) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "insert into san(name, description, location, loai_san_id, vi_tri_san, size, uid, poster, banner, is_approve) values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, san.getName());
        ps.setString(2, san.getDescription());
        ps.setString(3, san.getLocation());
        ps.setInt(4, san.getLoaiSan().getLoai_san_id());
        ps.setString(5, san.getVi_tri_san());
        ps.setString(6, san.getSize());
        ps.setInt(7, san.getUser().getUid());
        ps.setString(8,san.getPoster());
        ps.setString(9, san.getBanner());
        ps.setInt(10, san.getIs_approve());
        ps.executeUpdate();
        ps.close();
    }

    public San getSanById(int san_id) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from san where san_id = ?");
        ps.setInt(1, san_id);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int san_id1 = rs.getInt("san_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String location = rs.getString("location");
        int loai_san_id = rs.getInt("loai_san_id");
        LoaiSan loaiSan = loaiSanRepo.getLoaiSanById(loai_san_id);
        String vi_tri_san = rs.getString("vi_tri_san");
        String size = rs.getString("size");
        int uid = rs.getInt("uid");
        User user = userRepo.getUserById(uid);
        String poster = rs.getString("poster");
        String banner = rs.getString("banner");
        int is_approve = rs.getInt("is_approve");
        San san = new San(loai_san_id, name, description, location, loaiSan, vi_tri_san, size, user, poster, banner, is_approve, is_approve);
        return san;
    }

    public San getSanByName(String name) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from san where name = ?");
        ps.setString(1, name);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int san_id = rs.getInt("san_id");
        String name1 = rs.getString("name");
        String description = rs.getString("description");
        String location = rs.getString("location");
        int loai_san_id = rs.getInt("loai_san_id");
        LoaiSan loaiSan = loaiSanRepo.getLoaiSanById(loai_san_id);
        String vi_tri_san = rs.getString("vi_tri_san");
        String size = rs.getString("size");
        int uid = rs.getInt("uid");
        User user = userRepo.getUserById(uid);
        String poster = rs.getString("poster");
        String banner = rs.getString("banner");
        int is_approve = rs.getInt("is_approve");
        San san = new San(loai_san_id, name1, description, location, loaiSan, vi_tri_san, size, user, poster, banner, is_approve, is_approve);
        return san;
    }
}