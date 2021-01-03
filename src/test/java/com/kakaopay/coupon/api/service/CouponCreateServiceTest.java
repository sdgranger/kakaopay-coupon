package com.kakaopay.coupon.api.service;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponCodeGenerator;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CouponCreateServiceTest {
  @Mock
  private CouponRepository couponRepository;
  @Mock
  private CouponCodeGenerator couponCodeGenerator;
  @InjectMocks
  private CouponCreateService couponCreateService;

  @Test
  void 쿠폰_생성_테스트(){
    //given
    int amount = 5;
    List<Coupon> coupons = new ArrayList<>();
    for(int i=0; i<amount; i++){
      Coupon coupon = Coupon.create("test");
      coupons.add(coupon);
    }
    given(couponCodeGenerator.gen()).willReturn("test");
    given(couponRepository.saveAll(any())).willReturn(coupons);

    //when
    List<CouponDto> couponResponse = couponCreateService.create(amount);

    //then
    Assertions.assertEquals(couponResponse.size(), coupons.size());
    Assertions.assertEquals(couponResponse.get(0).getCode(), coupons.get(0).getCode());
  }
}
