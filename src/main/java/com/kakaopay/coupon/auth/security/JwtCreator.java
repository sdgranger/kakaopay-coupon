package com.kakaopay.coupon.auth.security;

import com.kakaopay.coupon.auth.domain.User;
import com.kakaopay.coupon.common.utils.ObjectUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtCreator {
  private int expiredMinuteTime = 1000;

  private String key;

  public JwtCreator(@Value("${jwt.authentication.key}") String key) {
    this.key = key;
  }

  public String createJwt(User user) {
    SecretKey secretKey;
    if(key == null){
      secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }else{
      secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }
    LocalDateTime currentTime = LocalDateTime.now();

    String jwtString = Jwts.builder().setHeaderParam("typ", "JWT")
            .claim("no", user.getNo())
            .claim("id", user.getId())
            .claim("roles", user.getRoles())
            .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(secretKey)
            .setExpiration(Date.from(currentTime.plusMinutes(expiredMinuteTime).atZone(ZoneId.systemDefault()).toInstant()))
            .compact();
    //Jws jws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtString);
    return jwtString;
  }

  public User parse(String rawAccessToken) {
    SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

    try{
      JwtParser parser = Jwts.parser();
      Jws<Claims> jwsClaims = parser.setSigningKey(secretKey).parseClaimsJws(rawAccessToken);

      Claims claims = jwsClaims.getBody();

      User user = new User();
      ObjectUtil.convertMapToObject(claims, user);

      return user;

    } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ex) {
      throw new BadCredentialsException("Invalid JWT token: ", ex);
    } catch (ExpiredJwtException expiredEx) {
      throw new JwtExpiredTokenException("JWT Token expired", expiredEx);
    }
  }
}
