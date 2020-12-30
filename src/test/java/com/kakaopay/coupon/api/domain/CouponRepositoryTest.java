package com.kakaopay.coupon.api.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("local")
public class CouponRepositoryTest {
  @Autowired
  private CouponRepository couponRepository;

  @Test
  @Rollback(false)
  void save(){
    Coupon coupon = Coupon.builder().code("testCode").status(Status.CREATED).build();

    couponRepository.save(coupon);
  }
}
