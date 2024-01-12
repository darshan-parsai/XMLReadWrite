package com.XMLReadWrite.XML.controller;

import com.XMLReadWrite.XML.entity.UserEntity;
import com.XMLReadWrite.XML.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user-controller")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save-user")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity userEntity){
        return new ResponseEntity<>(userService.saveUser(userEntity), HttpStatus.OK);
    }

    @GetMapping("/get-all-user")
    public List<UserEntity> userEntity(){
        return userService.getAllUser();
    }

    @PostMapping("/save-users-from-xml")
    public void saveUsersFromXml(@RequestParam MultipartFile xmlFile) throws IOException {
        userService.saveUsersFromXml(xmlFile);
    }
}
