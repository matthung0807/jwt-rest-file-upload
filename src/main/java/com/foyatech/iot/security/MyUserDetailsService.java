package com.foyatech.iot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.foyatech.iot.model.User;
import com.foyatech.iot.service.UserService;


public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	/**
	 * 取出Spring Security的使用者資設定
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  User user = userService.findUserByName(username);
	  
	  if(user == null) {
	  	throw new UsernameNotFoundException("User \""+ username + "\" is not existed");
	  }
	  
	return new UserPrincipal(
			          user.getUsername(), 
			          user.getPassword(), 
								user.isEnabled(), 
								user.isAccountNonExpired(), 
								user.isAccountNonLocked(), 
								user.isCredentialsNonExpired(),
								user.getAuthorities());
	
	
	}

}
