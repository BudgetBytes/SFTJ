package com.byter.sftj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byter.sftj.model.File;

@Repository
public interface FileRepo extends JpaRepository<File, Long>{

}
