package com.hexagon.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexagon.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	User findByUsuario(String usuario);
}
