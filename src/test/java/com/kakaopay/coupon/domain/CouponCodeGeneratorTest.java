package com.kakaopay.coupon.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CouponCodeGeneratorTest {

  @Test
  void 랜덤_쿠폰_생성_유효성검사() {
    CouponCodeGenerator codeGenerator = new CouponCodeGenerator();
    String genString = codeGenerator.gen();
    boolean isValid = codeGenerator.isValid(genString);

    Assertions.assertEquals(isValid, true);
  }
}
