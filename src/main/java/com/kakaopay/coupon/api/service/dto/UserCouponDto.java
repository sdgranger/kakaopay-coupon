package com.kakaopay.coupon.api.service.dto;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.Status;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserCouponDto {
  private String code;
  private Status status;
  private Long userNo;
  private LocalDateTime publishedAt;
  private LocalDate expirationDate;

  public UserCouponDto(Coupon coupon) {
    code = coupon.getCode();
    status = coupon.currentStatus();
    userNo = coupon.getUserNo();
    publishedAt = coupon.getPublishedAt();
    expirationDate = coupon.getExpirationDate();
  }
}
