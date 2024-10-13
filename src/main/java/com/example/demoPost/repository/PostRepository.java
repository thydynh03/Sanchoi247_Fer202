package com.example.demoPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoPost.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}