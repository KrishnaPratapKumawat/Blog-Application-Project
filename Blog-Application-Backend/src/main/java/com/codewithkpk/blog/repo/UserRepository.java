package com.codewithkpk.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithkpk.blog.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String email);

}