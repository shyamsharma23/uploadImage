package com.AZIZ.uploadImage.services;

import com.AZIZ.uploadImage.entities.User;
import com.AZIZ.uploadImage.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;






    public User create(User user){

        User user1 = userRepository.save(user);
        return user1;
    }

    public List<User> getUsers(){
        List<User> userList = userRepository.findAll();
        return userList;
    }

    public Optional<User> getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    public User getUserById(long id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        return user;
    }

    public User updateUser(long id, User user){
        User user1 = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setImageName(user.getImageName());
        userRepository.save(user1);

        return user1;
    }

    public String deleteUser(long id){
        userRepository.deleteById(id);
        return"The user has been deleted";

    }
}
