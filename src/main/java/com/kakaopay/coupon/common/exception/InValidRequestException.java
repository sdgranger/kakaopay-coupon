package com.kakaopay.coupon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InValidRequestException extends CustomException {

  public InValidRequestException(String message) {
    super("E400", message);
  }
}
