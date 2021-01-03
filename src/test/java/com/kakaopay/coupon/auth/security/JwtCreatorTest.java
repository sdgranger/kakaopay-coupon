package com.kakaopay.coupon.auth.security;

import com.kakaopay.coupon.auth.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class JwtCreatorTest {
  private JwtCreator jwtCreator;

  @Test
  void JWT_토큰발급_검증테스트(){
    //given
    String id = "testId";
    String pw = "testPw";

    User user = new User(id, pw);
    jwtCreator = new JwtCreator("testKey-ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    String token = jwtCreator.createJwt(user);

    //when
    User parseUser = jwtCreator.parse(token);

    //then
    assertEquals(user.getId(), parseUser.getId());
  }

}
