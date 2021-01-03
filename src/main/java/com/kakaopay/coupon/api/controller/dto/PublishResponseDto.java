package com.kakaopay.coupon.api.controller.dto;

import lombok.Getter;


@Getter
public class PublishResponseDto {
  private String couponCode;

  public PublishResponseDto(String couponCode) {
    this.couponCode = couponCode;
  }
}
