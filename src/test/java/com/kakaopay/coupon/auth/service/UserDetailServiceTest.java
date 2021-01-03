package com.kakaopay.coupon.auth.service;

import com.kakaopay.coupon.auth.domain.User;
import com.kakaopay.coupon.auth.domain.UserRepository;
import com.kakaopay.coupon.auth.security.JwtCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {
  @Mock
  private JwtCreator jwtCreator;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserDetailService userDetailService;

  @Test
  void 회원가입_테스트(){
    //given
    String id = "testId";
    String pw = "testPw";
    String createToken = "testToken";

    given(userRepository.save(any(User.class)))
            .willAnswer(i -> i.getArgument(0));
    given(jwtCreator.createJwt(any(User.class))).willReturn(createToken);

    //when
    String token = userDetailService.signUp(id, pw);

    //then
    assertEquals(token, createToken);
  }

  @Test
  void 로그인_테스트(){
    //given
    String id = "testId";
    String pw = "testPw";
    String createToken = "testToken";

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    User user = new User(id, passwordEncoder.encode(pw));
    given(userDetailService.loadUserByUsername(id))
            .willReturn(user);
    given(jwtCreator.createJwt(user)).willReturn(createToken);

    //when
    String token = userDetailService.signIn(id, pw);

    //then
    assertEquals(token, createToken);
  }
}
