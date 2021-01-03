package com.kakaopay.coupon.api.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CouponCodeGeneratorTest {

  @Test
  void 랜덤_쿠폰_생성_유효성검사() {
    //given
    CouponCodeGenerator codeGenerator = new CouponCodeGenerator();
    String genString = codeGenerator.gen();

    //when
    boolean isValid = codeGenerator.isValid(genString);

    //then
    Assertions.assertEquals(isValid, true);
  }
}
