package com.kakaopay.coupon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedLoginException extends CustomException {

  public FailedLoginException(String message) {
    super("L400", message);
  }
}
