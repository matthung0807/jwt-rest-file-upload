package com.foyatech.iot.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
//	@Value("${jwt.secret}")
  private static final String secret = "MTIz";// Base64 encode "123"

  
  private static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
  /**
   * 解析JWT,成功回傳使用者資訊,失敗回傳null
   */
  public static UserPrincipal parseToken(String token) {
    try {
    	
    	// Claims就是JWT的payload部分
    	// setSigningKey(String base64EncodedKeyBytes)只吃base64編碼的字串, 傳入無法base64解碼的字串會發生錯誤
      Claims body = Jwts.parser()
              .setSigningKey(secret)
              .parseClaimsJws(token)
              .getBody();
      				
      String username = (String) body.get("name");
      String password = (String) body.get("password");
      String role = (String) body.get("role");
      
      // 以下設定會影響Spring Security是否讓此帳號通過驗證
      boolean enabled = true;               // 此帳號是否啟用
      boolean accountNonExpired = true;     // 此帳號是否未過期
      boolean credentialsNonExpired = true; // 此憑證是否過期
      boolean accountNonLocked = true;      // 此帳號是否鎖住
      
      
      UserPrincipal user = new UserPrincipal(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, role);

      return user;

    } catch (JwtException | ClassCastException e) {
      return null;
    } catch (Exception e) {
    	return null;
    }
  }

  /**
   * 產生 JWT token, payload中裝載name, password, role
   */
  public static String generateToken(UserPrincipal user) {
    Claims claims = Jwts.claims().setSubject(null);
    claims.put("name", user.getUsername());
    claims.put("password", user.getPassword());
    claims.put("role", user.getRole());

    byte[] secretKey = new String("123").getBytes();
    return Jwts.builder()
              .setClaims(claims)
              .signWith(signatureAlgorithm, secretKey)
              .compact();
  }
  
}
