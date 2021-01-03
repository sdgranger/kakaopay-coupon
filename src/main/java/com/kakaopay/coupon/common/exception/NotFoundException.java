package com.kakaopay.coupon.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends CustomException {

  public NotFoundException() {
    super("E400", null);
  }

  public NotFoundException(String message) {
    super("O400", message);
  }
}
