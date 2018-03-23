package com.foyatech.iot.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * AuthenticationManager收到Authencatication後會轉交ProviderManager來進行驗證
 * ProviderManager根據所設定的AuthenticationProvider來進行驗證
 * @author matthung
 *
 */
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	
	@Override
  public boolean supports(Class<?> authentication) {
		System.out.println("JwtAuthenticationProvider.supports()...");
      return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }
	
	/**
	 * 原本進行驗證的邏輯放在這,但JWT在解析token時即為驗證,所以不做任何事
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
		throws AuthenticationException {
	  System.out.println("JwtAuthenticationProvider.additionalAuthenticationChecks()...");
	  // 此方法用來做驗證, 但JWT在從token時就必須驗證, 所以這邊不用做任何事
	}
  
	/**
	 * 從authentication中取出token並解析來取得使用者資料,
	 * 如果無法解析出使用者代表token是錯的
	 */
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
		throws AuthenticationException {
		System.out.println("JwtAuthenticationProvider.retrieveUser()...");
		
		// authentication物件從AuthenticationManager傳過來
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    String token = jwtAuthenticationToken.getToken();
    System.out.println(token);

    UserPrincipal parsedUser = JwtUtil.parseToken(token);
    if (parsedUser == null) {
        throw new JwtTokenMalformedException("JWT token is not valid");
    }

    return parsedUser;
	}

}
