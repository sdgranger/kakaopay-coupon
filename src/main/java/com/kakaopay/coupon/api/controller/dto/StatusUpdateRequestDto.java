package com.kakaopay.coupon.api.controller.dto;

import com.kakaopay.coupon.api.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateRequestDto {
  private Status status;
}
