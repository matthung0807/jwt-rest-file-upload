package com.foyatech.iot.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
  /**
   * 登入成功將JWT放在cookie並透過Response回傳至前端
   */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
	  UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
	  
	  System.out.println(principal.getToken());
	  Cookie cookie = new Cookie("jwt",principal.getToken());
	  response.addCookie(cookie);
	  
	}

}
