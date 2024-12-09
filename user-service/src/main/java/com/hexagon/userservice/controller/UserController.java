package com.hexagon.userservice.controller;
import com.hexagon.userservice.entity.User;
import com.hexagon.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping(value = "/api/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public boolean verificarLogin(@RequestBody User user) {
    	return userService.verificarLogin(user.getUsuario(), user.getSenha());
    }
    
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User newUser = userService.addUser(user);  // Call service to save the user
            return ResponseEntity.status(201).body(newUser);  // Return the created user
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(null);  // Handle validation or other errors
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);  // Handle unexpected errors
        }
    }
    

    @GetMapping
    public List<User> fetchUsers(){
        return userService.fetchUsers();
    }

    @GetMapping("/{id}")
    public User fetchUserById(@PathVariable int id){
        return userService.fetchUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}