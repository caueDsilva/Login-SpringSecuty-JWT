package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.user.User;

public interface UserRepository extends JpaRepository<User, String> {


    
}
