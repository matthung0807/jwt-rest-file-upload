package com.foyatech.iot.service;

import com.foyatech.iot.model.User;

public interface UserService {
	
	public User findUserByName(String username);

}
