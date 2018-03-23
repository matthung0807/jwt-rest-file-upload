package com.foyatech.iot.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Spring Security用來裝載使用者資訊的類別
 * @author matthung
 *
 */
public class UserPrincipal extends org.springframework.security.core.userdetails.User{
	private static final long serialVersionUID = 1L;
	
	public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
		boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
	  super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
											 boolean credentialsNonExpired, boolean accountNonLocked, String authorities) {
		
		  this(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
		  this.role = authorities;
		  this.token = JwtUtil.generateToken(this);
  }

  private String role;
  private String token;
  
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
