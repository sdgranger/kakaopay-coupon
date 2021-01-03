package com.kakaopay.coupon.api.service;


import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.domain.Status;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import com.kakaopay.coupon.api.service.dto.UserCouponDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponFindService {
  private final CouponRepository couponRepository;

  public List<UserCouponDto> find(Long userNo) {
    List<Coupon> coupon = couponRepository.findAllByUserNo(userNo);

    return coupon.stream().map(UserCouponDto::new).collect(Collectors.toList());
  }

  public List<CouponDto> findAllExpiredCoupon() {
    List<Coupon> coupons = couponRepository.findAllByExpirationDateBeforeAndStatus(LocalDate.now(), Status.EXPIRED);

    return coupons.stream().map(CouponDto::new).collect(Collectors.toList());
  }
}
