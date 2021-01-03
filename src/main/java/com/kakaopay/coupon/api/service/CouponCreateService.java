package com.kakaopay.coupon.api.service;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponCodeGenerator;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponCreateService {
  private final CouponRepository couponRepository;
  private final CouponCodeGenerator couponCodeGenerator;

  public List<CouponDto> create(int n) {
    List<Coupon> coupons = new ArrayList<>();

    List<CouponDto> couponResponse = new ArrayList<>();

    for (int i = 0; i < n; i++) {
        Coupon coupon = Coupon.create(couponCodeGenerator.gen());
        coupons.add(coupon);
        couponResponse.add(new CouponDto(coupon));
    }

    couponRepository.saveAll(coupons);

    return couponResponse;
  }
}
