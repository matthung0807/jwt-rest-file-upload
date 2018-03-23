package com.foyatech.iot.security;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMalformedException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public JwtTokenMalformedException(String msg) {
	  super(msg);
	}

}
