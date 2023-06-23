package com.AZIZ.uploadImage.controllers;

import com.AZIZ.uploadImage.entities.ImageResponse;
import com.AZIZ.uploadImage.entities.User;
import com.AZIZ.uploadImage.services.FileServiceImpl;
import com.AZIZ.uploadImage.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    UserService userService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    @PostMapping("/images/{userId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @RequestParam("userImage") MultipartFile image,
            @PathVariable("userId") long userId ) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);//Uploaded the file but the name is not uploaded yet
        User user = userService.getUserById(userId);

        user.setImageName(imageName); //Set the image name of the image that is uploaded by the client

        userService.updateUser(userId, user); //Saving of user image name into database

        ImageResponse imageResponse = ImageResponse.builder()
                .imageName(imageName)
                .message("Image uploaded")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    @GetMapping("images/{userId}")
    public void getUserImage(
            @PathVariable("userId") long id,
            HttpServletResponse response
    ) throws IOException {

        User user = userService.getUserById(id);
        InputStream inputStream = fileService.getResource(imageUploadPath,user.getImageName());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());

    }


}
