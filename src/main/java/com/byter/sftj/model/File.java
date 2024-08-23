package com.byter.sftj.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table
public class File implements Serializable {
	private static final long serialVersionUID = -7374832899093995246L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column
	private String name;
	@Column
	private String path;
	@Column
	private Date uploadedAt;
	@JoinColumn(name = "username")
	private String username;

	public File() {}
	
	public File(String name, String path, Date uploadedAt, String username) {
		super();
		this.name = name;
		this.path = path;
		this.uploadedAt = uploadedAt;
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Date getUploadedAt() {
		return uploadedAt;
	}


	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "File [id=" + id + ", name=" + name + ", path=" + path + "]";
	}
}
