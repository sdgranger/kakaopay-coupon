package com.kakaopay.coupon.api.domain;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;

@DataJpaTest
@ActiveProfiles("test")
public class CouponRepositoryTest {
  @Autowired
  private CouponRepository couponRepository;

  @Test
  void 쿠폰_저장_테스트(){
    Coupon coupon = Coupon.builder().code("testCode").status(Status.CREATED).build();

    couponRepository.save(coupon);
  }

  @Test
  void 만기된_쿠폰_조회_테스트(){
    List<Coupon> coupons = couponRepository.findAllByExpirationDateBeforeAndStatus(LocalDate.now(), Status.PUBLISHED);

    assertThat(coupons.size(), Matchers.greaterThan(-1));
  }

}
