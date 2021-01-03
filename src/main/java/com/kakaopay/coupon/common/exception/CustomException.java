package com.kakaopay.coupon.common.exception;

public class CustomException extends RuntimeException {
  private String errorCode;


  public CustomException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}