package org.example.day31jpaexercisecapstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Api.ApiResponse;
import org.example.day31jpaexercisecapstone1.Model.User;
import org.example.day31jpaexercisecapstone1.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors err){
        if(err.hasErrors()){
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }
        userService.addUser(user);
            return ResponseEntity.ok(new ApiResponse("User registered Successfully"));
    }
    @PutMapping("/update/{ID}")
    public ResponseEntity<?> updateUser(@PathVariable Integer ID,@Valid @RequestBody User user, Errors err){
        if(err.hasErrors()){
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }
        if(userService.updateUser(ID,user)){
            return ResponseEntity.ok(new ApiResponse("User Updated Successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Category ID Not Found"));
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer ID){
        if(userService.deleteUser(ID)){
            return ResponseEntity.ok(new ApiResponse("User Deleted Successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User ID Not Found"));

    }
}
