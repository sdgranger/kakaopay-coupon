package com.kakaopay.coupon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAllowedException extends CustomException {

  public NotAllowedException(String message) {
    super("A400", message);
  }
}
