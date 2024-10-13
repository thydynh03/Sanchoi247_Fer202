package com.example.demoPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoPost.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
