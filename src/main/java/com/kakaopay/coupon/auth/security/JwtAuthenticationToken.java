package com.kakaopay.coupon.auth.security;

import com.kakaopay.coupon.auth.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
  private static final long serialVersionUID = 2877954820905567501L;

  private String rawAccessToken;
  private User user;

  public JwtAuthenticationToken(String token) {
    super(null);
    this.rawAccessToken = token;
    this.setAuthenticated(false);
  }

  public JwtAuthenticationToken(String unsafeToken, User user) {
    super(user.getAuthorities());
    this.rawAccessToken = unsafeToken;
    this.user = user;
    super.setAuthenticated(true);
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    if (authenticated) {
      throw new IllegalArgumentException(
              "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    }
    super.setAuthenticated(false);
  }

  @Override
  public Object getCredentials() {
    return rawAccessToken;
  }

  @Override
  public Object getPrincipal() {
    return this.user;
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
    this.rawAccessToken = null;
  }
}
