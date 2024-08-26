package com.byter.sftj.utils;

import java.time.temporal.ChronoUnit;

public interface Constants {
	String BASE_DIR = System.getProperty("user.home") + "/.sftj";
	String SESSION_JWT = "bearer";
	String SESSION_USERNAME = "username";
	String REGISTER_ERR = "registerErr";
	String LOGIN_ERR = "loginErr";
	String DAHSBOARD_ERR = "dashboardErr";
	String USER_FILES = "userFiles";
	String JWT_SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
	long JWT_EXPIRATION = 24l; // 1 giorgno
	ChronoUnit UNIT = ChronoUnit.HOURS;
}
