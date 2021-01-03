package com.kakaopay.coupon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InValidStatusException extends CustomException {

  public InValidStatusException() {
    super("S400", null);
  }

  public InValidStatusException(String message) {
    super("S400",message);
  }
}
