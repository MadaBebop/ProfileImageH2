package com.example.filehandler.Service;

import com.example.filehandler.Entities.User;
import com.example.filehandler.Repository.UserRepository;
import com.example.filehandler.Utils.imageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long createUser(User user){
        userRepository.save(user);
        return user.getId();
    }

    //Upload profile picture
    public String uploadProfilePicture(Long id,MultipartFile file) throws IOException {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().setProfilePicture(imageUtils.compressImg(file.getBytes()));  //compression
            userRepository.save(user.get());
            return "profile picture updated successfully";
        }
        return "User not found";
    }

    //Download profile picture
    public byte[] downloadProfilePicture(long id) throws IOException {
        Optional<User> user = userRepository.findById(id);
        //decompression here
        if (user.isPresent()){
            //decompression
            return imageUtils.decompressImg(user.get().getProfilePicture());
        }
        return null;
    }

}
