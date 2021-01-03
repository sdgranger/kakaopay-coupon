package com.kakaopay.coupon.api.service;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.domain.Status;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import com.kakaopay.coupon.api.service.dto.UserCouponDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
public class CouponFindServiceTest {
  @Mock
  private CouponRepository couponRepository;
  @InjectMocks
  private CouponFindService couponFindService;

  @Test
  void 유저_쿠폰_조회_테스트(){
    //given
    Long userNo = 0L;
    List<Coupon> coupons = new ArrayList<>();
    Coupon coupon = Coupon.create("test");
    coupons.add(coupon);

    coupon.publish(userNo);

    given(couponRepository.findAllByUserNo(userNo)).willReturn(coupons);

    //when
    List<UserCouponDto> dtos = couponFindService.find(userNo);

    //then
    assertThat(dtos.size(), Matchers.greaterThan(0));
    assertEquals(dtos.get(0).getStatus(), Status.PUBLISHED);
    assertNotNull(dtos.get(0).getUserNo());
    assertNotNull(dtos.get(0).getExpirationDate());
    assertNotNull(dtos.get(0).getPublishedAt());
  }

  void 유저_쿠폰_미존재_테스트(){

  }

  @Test
  void 만료쿠폰_전체조회_테스트(){
    //given
    Long userNo = 0L;
    List<Coupon> coupons = new ArrayList<>();
    Coupon givenCoupon = spy(Coupon.create("test"));
    given(givenCoupon.expired()).willReturn(true);
    given(couponRepository.findAllByExpirationDateBeforeAndStatus(LocalDate.now(), Status.EXPIRED))
            .willReturn(coupons);

    givenCoupon.publish(userNo);

    coupons.add(givenCoupon);

    //when
    List<CouponDto> dtos = couponFindService.findAllExpiredCoupon();

    //then
    assertThat(dtos.size(), Matchers.greaterThan(0));
    assertEquals(dtos.get(0).getStatus(), Status.EXPIRED);
    assertEquals(dtos.get(0).getExpirationDate(), LocalDate.now().plusMonths(1));
    assertNotNull(dtos.get(0).getUserNo());
    assertNotNull(dtos.get(0).getPublishedAt());
  }
}
