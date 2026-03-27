package com.kishansetu.kishan_setu_backend.controller;


import com.kishansetu.kishan_setu_backend.model.UserModel;
import com.kishansetu.kishan_setu_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    /// method to get all users
    @GetMapping
    List<UserModel> getAllUsers(){
        return userService.getAllUsers();
    }

    ///  method for login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel userModel){
        return userService.loginUser(userModel);
    }
    ///  method for register
    @PostMapping("/register")
    public ResponseEntity<?> saveUSer(@RequestBody UserModel userModel){
        return  userService.saveUser(userModel);
    }
    ///  method for edit existing user
    @PutMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUser(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) MultipartFile image
    ) {

        UserModel user = new UserModel();

        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole(role);

        return userService.updateUser(user, image);
    }

    ///  method for delete
    @DeleteMapping("/{userID}")
    public  ResponseEntity<?> deleteUser(@PathVariable String userID){
        return  userService.deleteUser(userID);
    }






}
