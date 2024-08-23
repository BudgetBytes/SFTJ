package com.byter.sftj.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class User implements Serializable{
	private static final long serialVersionUID = 4321970036381035132L;
	
	@Id
	private String username;
	@Column
	private String password;
	@OneToMany(cascade = CascadeType.ALL,  mappedBy = "username")
	private List<File> files;

	public User() {}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public List<File> getFiles() {
		return files;
	}
	public void setFiles(List<File> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", files=" + files + "]";
	}
}
