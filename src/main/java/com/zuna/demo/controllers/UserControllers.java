package com.zuna.demo.controllers;
import com.zuna.demo.Repository.UserRepository;
import com.zuna.demo.models.Users;
import com.zuna.demo.userEntity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import com.zuna.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserControllers {
    // @GetMapping
    // public String getUsers() {
    //     return "List of users for testing purposes.";
    // }

    // @GetMapping
    // public List<Users> getUsers(){
    //     return Arrays.asList(
    //         new Users(1L, "John", "john@gmail.com"),
    //         new Users(2L, "Kiwi", "kiwi@gmail.com"),
    //         new Users(3L, "Smith", "smith@gmail.com")
    //     );
    // }
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity user){
        System.out.println("User created: " + user.getName() + ", " + user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@RequestBody UserEntity user, @PathVariable Long id){
        UserEntity userData = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userData.setName(user.getName());
        userData.setEmail(user.getEmail());
        return userRepository.save(userData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        UserEntity userData = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(userData);
        return ResponseEntity.ok().build();
    }
}
