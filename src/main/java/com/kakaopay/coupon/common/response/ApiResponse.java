package com.kakaopay.coupon.common.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
  private int code;

  private String status;

  private T data;

  private String message;

  public ApiResponse(HttpStatus httpStatus, T data) {
    this.data = data;
    this.status = String.valueOf(httpStatus.value());
  }

  public static <T> ApiResponse<T> ok(T o) {
    return new ApiResponse(HttpStatus.OK, o);
  }
}
