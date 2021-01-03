package com.kakaopay.coupon.auth.security;

import com.kakaopay.coupon.auth.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthManager {
  public AuthManager() {
  }

  public User getLoginUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal != null && principal instanceof User) {
        return (User) principal;
      }

    }
    return null;
  }

  public boolean isLoginUser(Long userNo){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal != null && principal instanceof User) {
        if(userNo == ((User) principal).getNo())
        return true;
      }

    }
    return false;
  }
}
