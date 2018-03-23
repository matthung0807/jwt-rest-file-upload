package com.foyatech.iot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.foyatech.iot.model.User;


@Service
public class UserServiceImpl implements UserService {
	
	/**
	 * 依照使用者名稱username至資料庫查找使用者
	 */
	@Override
	public User findUserByName(String usernmae) {
		List<User> userList = genUserList();
		for(User user : userList) {
			if(user.getUsername().equals(usernmae)) {
				return user;
			}
		}
		
		return null;
	}
  
	/**
	 * 假的資料庫現有的使用者
	 * @return
	 */
	private List<User> genUserList(){
		List<User> userList = new ArrayList<User>();
		User matt = new User();
		matt.setUsername("matt");
		matt.setPassword("matt");
		matt.setEnabled(true);
		matt.setAccountNonExpired(true);
		matt.setAccountNonLocked(true);
		matt.setCredentialsNonExpired(true);
		matt.setAuthorities("ROLE_ADMIN");
		userList.add(matt);
		
		User john = new User();
		john.setUsername("john");
		john.setPassword("john");
		john.setEnabled(true);
		john.setAccountNonExpired(true);
		john.setAccountNonLocked(true);
		john.setCredentialsNonExpired(true);
		john.setAuthorities("ROLE_USER");
		userList.add(john);
		
		return userList;
	}
	
}
