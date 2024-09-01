package com.byter.sftj;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.byter.sftj.utils.Constants;

@SpringBootApplication
public class SftjApplication implements Constants{

	public static void main(String[] args) {
		File baseDir = new File(BASE_DIR);
		if (!baseDir.exists()){
			baseDir.mkdirs();
		}
		SpringApplication.run(SftjApplication.class, args);
	}

}
