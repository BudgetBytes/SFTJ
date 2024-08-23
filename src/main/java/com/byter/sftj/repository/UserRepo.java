package com.byter.sftj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byter.sftj.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

}
