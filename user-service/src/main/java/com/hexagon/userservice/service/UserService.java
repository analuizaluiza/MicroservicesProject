package com.hexagon.userservice.service;
import com.hexagon.userservice.entity.User;
import com.hexagon.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> fetchUsers(){
        return userRepository.findAll();
    }

    public User fetchUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> updateUser(int id, User updatedUser) {
        try {
            Optional<User> schoolOptional = userRepository.findById(id);
            if (schoolOptional.isPresent()) {
                User user = schoolOptional.get();
                user.setNome(updatedUser.getNome());
                user.setUsuario(updatedUser.getUsuario());
                user.setSenha(updatedUser.getSenha());
                
                User savedUser = userRepository.save(user);
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteUser(int id) {
        try {
            Optional<User> school = userRepository.findById(id);
            if (school.isPresent()) {
                userRepository.deleteById(id);
                return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


	public boolean verificarLogin(String usuario, String senha) {
		User user = userRepository.findByUsuario(usuario);
		if (user != null && user.getSenha().equals(senha)) {
			return true;
		}
		
		return false;
	}
}