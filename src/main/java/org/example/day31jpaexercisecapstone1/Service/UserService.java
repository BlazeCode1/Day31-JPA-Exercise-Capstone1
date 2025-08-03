package org.example.day31jpaexercisecapstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Model.User;
import org.example.day31jpaexercisecapstone1.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }


    public boolean updateUser(Integer ID,User user){
        User oldUser = userRepository.getById(ID);
        if(oldUser==null)
            return false;
        oldUser.setUsername(user.getUsername());
        oldUser.setRole(user.getRole());
        oldUser.setBalance(user.getBalance());
        oldUser.setEmail(user.getEmail());
        userRepository.save(oldUser);
        return true;
    }

    public boolean deleteUser(Integer ID){
        User user = userRepository.getById(ID);
        if(user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    public User findById(Integer ID){
        return userRepository.getById(ID);
    }





}
