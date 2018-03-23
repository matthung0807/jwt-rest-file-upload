package com.foyatech.iot.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 用來封裝token
 * @author matthung
 *
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{
	private static final long serialVersionUID = 1L;
	private String token;

  public JwtAuthenticationToken(String token) {
      super(null, null);
      this.token = token;
  }

  public String getToken() {
      return token;
  }
  
}
