package com.kakaopay.coupon.auth.service;

import com.kakaopay.coupon.auth.security.JwtCreator;
import com.kakaopay.coupon.auth.domain.User;
import com.kakaopay.coupon.auth.domain.UserRepository;
import com.kakaopay.coupon.common.exception.FailedLoginException;
import com.kakaopay.coupon.common.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtCreator jwtCreator;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userRepository.findById(username);
  }

  @Transactional
  public String signUp(String id, String password) {
    User user = new User(id, passwordEncoder.encode(password));
    user.grantRole("ROLE_USER");

    userRepository.save(user);

    String token = jwtCreator.createJwt(user);
    return token;
  }

  @Transactional(readOnly = true)
  public String signIn(String id, String password) {
    User user = (User) loadUserByUsername(id);
    if(user == null){
      throw new NotFoundException();
    }

    if(!passwordEncoder.matches(password, user.getPassword())){
      throw new FailedLoginException("not match pw");
    }

    String token = jwtCreator.createJwt(user);
    return token;
  }
}
