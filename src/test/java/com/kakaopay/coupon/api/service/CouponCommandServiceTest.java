package com.kakaopay.coupon.api.service;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.domain.Status;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import com.kakaopay.coupon.auth.security.AuthManager;
import com.kakaopay.coupon.common.exception.InValidRequestException;
import com.kakaopay.coupon.common.exception.InValidStatusException;
import com.kakaopay.coupon.common.exception.NotAllowedException;
import com.kakaopay.coupon.common.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CouponCommandServiceTest {
  @Mock
  private CouponRepository couponRepository;
  @Mock
  private AuthManager authManager;
  @InjectMocks
  private CouponCommandService couponCommandService;

  @Test
  void 사용자_쿠폰발급_테스트(){
    //given
    Long userNo = 0L;
    String couponReqCode = "test";

    Coupon coupon = Coupon.create("test");

    given(couponRepository.findByCode(couponReqCode)).willReturn(coupon);

    //when
    CouponDto dto = couponCommandService.publish(couponReqCode, userNo);

    //then
    Assertions.assertEquals(dto.getCode(), couponReqCode);
    Assertions.assertEquals(dto.getStatus(), Status.PUBLISHED);
    Assertions.assertEquals(dto.getUserNo(), userNo);
  }

  @Test
  void 쿠폰_미존재시_실패_테스트(){
    //given
    Long userNo = 0L;

    given(couponRepository.findByCode(any())).willReturn(null);

    Assertions.assertThrows(NotFoundException.class, () ->
            couponCommandService.publish("test2", userNo));
  }

  @Test
  void 쿠폰_유저사용_테스트(){
    //given
    String couponReqCode = "test";
    Long userNo = 0L;
    Coupon coupon = Coupon.create("test");
    coupon.publish(userNo);
    given(couponRepository.findByCode(couponReqCode)).willReturn(coupon);
    given(authManager.isLoginUser(userNo)).willReturn(true);

    //when
    CouponDto useCoupon = couponCommandService.use(couponReqCode);

    //then
    Assertions.assertEquals(useCoupon.getCode(), couponReqCode);
    Assertions.assertEquals(useCoupon.getStatus(), Status.USED);
  }

  @Test
  void 쿠폰_유저사용_권한실패_테스트(){
    //given
    String couponReqCode = "test";
    Long userNo = 0L;
    Coupon coupon = Coupon.create("test");
    coupon.publish(userNo);
    given(couponRepository.findByCode(couponReqCode)).willReturn(coupon);
    given(authManager.isLoginUser(userNo)).willReturn(false);

    //then
    Assertions.assertThrows(NotAllowedException.class, () ->
            //when
            couponCommandService.use(couponReqCode));

  }

  @Test
  void 쿠폰_취소_테스트(){
    //given
    String couponReqCode = "test";
    Long userNo = 0L;

    Coupon coupon = Coupon.create("test");
    coupon.publish(userNo);
    coupon.use();
    given(couponRepository.findByCode(couponReqCode)).willReturn(coupon);
    given(authManager.isLoginUser(userNo)).willReturn(true);

    //when
    CouponDto useCoupon = couponCommandService.cancel(couponReqCode);

    //then
    Assertions.assertEquals(useCoupon.getCode(), couponReqCode);
    Assertions.assertEquals(useCoupon.getStatus(), Status.PUBLISHED);
  }

  @Test
  void 발급되지않은_쿠폰_취소실패_테스트(){
    String couponReqCode = "test";

    Coupon coupon = Coupon.create("test");
    given(couponRepository.findByCode(couponReqCode)).willReturn(coupon);

    Assertions.assertThrows(InValidRequestException.class, () ->
            couponCommandService.cancel(couponReqCode));
  }

  @Test
  void 사용되지않은_쿠폰_취소실패_테스트(){
    String couponReqCode = "test";
    Long userNo = 0L;

    Coupon coupon = Coupon.create("test");
    coupon.publish(userNo);
    given(couponRepository.findByCode(couponReqCode)).willReturn(coupon);
    given(authManager.isLoginUser(userNo)).willReturn(true);

    Assertions.assertThrows(InValidStatusException.class, () ->
            couponCommandService.cancel(couponReqCode));
  }

}
