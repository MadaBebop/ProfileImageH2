package com.example.filehandler.Controller;

import com.example.filehandler.Entities.User;
import com.example.filehandler.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/img")
    public ResponseEntity<?> uploadImage(Long id, @RequestParam("image") MultipartFile image) throws IOException {
        String uploaded = userService.uploadProfilePicture(id, image);
        return ResponseEntity.status(HttpStatus.OK).body(uploaded);
    }

    @GetMapping("/img/download")
    public ResponseEntity<?> downloadImage(Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpeg"))
                .body(userService.downloadProfilePicture(id));
    }

    @PostMapping ("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

}
