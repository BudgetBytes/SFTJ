package com.byter.sftj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.byter.sftj.model.File;
import com.byter.sftj.repository.FileRepo;

@Service
public class FileService {
	@Autowired
	private FileRepo repo;
	
	public File save(File file) {
		return repo.save(file);
	}
	
	public Optional<File> findById(Long id) {
		return repo.findById(id);
	}
	
	public List<File> findAll() {
		return repo.findAll();
	}
	
	public void delete(File file) {
		repo.delete(file);
	}
}
