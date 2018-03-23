package com.foyatech.iot.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

/**
 * 驗證JWT的filter
 * @author matthung
 *
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
	  super(defaultFilterProcessesUrl);
	}

	@Override
  protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
      return true;
  }
  
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException, IOException, ServletException {
		System.out.println("JwtAuthenticationFilter.attemptAuthentication()...");
		
		String header = request.getHeader("Authorization"); // 取得Request Headers中的Authorization

    if (header == null || !header.startsWith("Bearer ")) { // JWT以"Bearer"作為schema
        throw new JwtTokenMissingException("No JWT token found in request headers");
    }

    String authToken = header.substring(7); // 取得JWT

    JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken); // 以Authentication封裝token

    return getAuthenticationManager().authenticate(authRequest); // 將Authentication交給AuthenticationManager進行驗證
	}
  
	/**
	 * 驗證成功時會被呼叫
	 */
	@Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
          throws IOException, ServletException {
		
      super.successfulAuthentication(request, response, chain, authResult);
      System.out.println("JwtAuthenticationFilter.successfulAuthentication()...");
    
    chain.doFilter(request, response); // 驗證成功直接通過Filter導向資源位置
  }
}
