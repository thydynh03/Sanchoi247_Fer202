package com.example.SanChoi247.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.SanChoi247.model.entity.Message;

@Service
public class MessageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper để lấy dữ liệu liên quan đến message và thông tin người gửi
    private static final RowMapper<Message> messageRowMapper = (rs, rowNum) -> {
        Message message = new Message();
        message.setContent(rs.getString("content"));
        message.setSenderUid(rs.getInt("sender_uid"));
        message.setReceiverUid(rs.getInt("receiver_uid"));
        message.setTimestamp(rs.getTimestamp("timestamp"));

        // Thêm thông tin người gửi
        message.setSenderName(rs.getString("sender_name")); // Lấy tên người gửi từ truy vấn
        message.setSenderAvatar(rs.getString("sender_avatar")); // Lấy avatar người gửi từ truy vấn
        
        return message;
    };

    // Lấy danh sách tin nhắn theo senderUid, bao gồm thông tin người gửi
    public List<Message> getMessagesBySenderUid(int senderUid) {
        String sql = "SELECT m.*, u.name AS sender_name, u.avatar AS sender_avatar " +
                     "FROM messages m " +
                     "JOIN users u ON m.sender_uid = u.uid " +
                     "WHERE m.sender_uid = ? " +
                     "ORDER BY m.timestamp ASC";
        return jdbcTemplate.query(sql, messageRowMapper, senderUid);
    }
    

    // Lấy danh sách tin nhắn theo receiverUid, bao gồm thông tin người gửi
    public List<Message> getMessagesByReceiverUid(int receiverUid) {
        String sql = "SELECT m.*, u.name AS sender_name, u.avatar AS sender_avatar " +
                     "FROM messages m " +
                     "JOIN users u ON m.sender_uid = u.uid " + // Kết nối với bảng users để lấy thông tin
                     "WHERE m.receiver_uid = ? " +
                     "ORDER BY m.timestamp ASC";
        return jdbcTemplate.query(sql, messageRowMapper, receiverUid);
    }

    // Lưu tin nhắn vào cơ sở dữ liệu
    public void saveMessage(Message message) {
        String sql = "INSERT INTO messages (sender_uid, receiver_uid, content, timestamp) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, message.getSenderUid(), message.getReceiverUid(), message.getContent(), message.getTimestamp());
    }
}
