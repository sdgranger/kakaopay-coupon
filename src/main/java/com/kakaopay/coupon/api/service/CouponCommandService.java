package com.kakaopay.coupon.api.service;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import com.kakaopay.coupon.auth.security.AuthManager;
import com.kakaopay.coupon.common.exception.InValidRequestException;
import com.kakaopay.coupon.common.exception.NotAllowedException;
import com.kakaopay.coupon.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponCommandService {
  private final CouponRepository couponRepository;
  private final AuthManager authManager;

  public CouponDto publish(String couponCode, Long userNo) {
    Coupon coupon = couponRepository.findByCode(couponCode);
    if(coupon == null){
      throw new NotFoundException();
    }

    coupon.publish(userNo);

    return new CouponDto(coupon);
  }

  public CouponDto use(String couponCode) {
    Coupon coupon = couponRepository.findByCode(couponCode);

    if (coupon == null) {
      throw new NotFoundException();
    }

    if(!authManager.isLoginUser(coupon.getUserNo())){
      throw new NotAllowedException("can't request");
    }

    coupon.use();

    return new CouponDto(coupon);
  }

  public CouponDto cancel(String couponCode) {
    Coupon coupon = couponRepository.findByCode(couponCode);

    if(coupon.getUserNo() == null){
      throw new InValidRequestException("not published");
    }

    if(!authManager.isLoginUser(coupon.getUserNo())){
      throw new NotAllowedException("can't request");
    }

    coupon.cancel();

    return new CouponDto(coupon);
  }
}
