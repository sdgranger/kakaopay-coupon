package com.kakaopay.coupon.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishRequestDto {
  private String couponCode;
  private Long userNo;
}
