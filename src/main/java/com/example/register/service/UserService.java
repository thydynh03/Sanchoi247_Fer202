package com.example.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.register.model.entity.User;
import com.example.register.model.entity.UserRegistrationForm;
import com.example.register.model.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(UserRegistrationForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setDob(form.getDob());
        user.setGender(form.getGender());
        user.setPhone(form.getPhone());
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(User.UserRole.UNVERIFIED_USER); // Set role default

        userRepository.save(user);
    }
}

