package com.kakaopay.coupon.auth.security;

import com.kakaopay.coupon.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private JwtCreator jwtCreator;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String rawAccessToken = (String) authentication.getCredentials();

    User user = jwtCreator.parse(rawAccessToken);

    return new JwtAuthenticationToken(rawAccessToken, user);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
