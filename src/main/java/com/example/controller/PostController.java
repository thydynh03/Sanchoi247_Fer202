package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demoPost.model.Comment;
import com.example.demoPost.model.Post;
import com.example.demoPost.repository.CommentRepository;
import com.example.demoPost.repository.PostRepository;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    public PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // @GetMapping("/new")
    // public String createPostForm(Model model) {
    //     model.addAttribute("post", new Post());
    //     return "create_post";
    // }
    @GetMapping("/")
    public String showIndex(){
        return "create_post";
    }

    @PostMapping
    public String savePost(@ModelAttribute Post post) {
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "post_list";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());
        return "view_post";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @ModelAttribute Comment comment) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post Id:" + id));
        comment.setPost(post);
        commentRepository.save(comment);
        return "redirect:/posts/" + id;
    }
}
