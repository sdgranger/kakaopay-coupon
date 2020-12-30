package com.kakaopay.coupon.api.dto;

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
        this.code = httpStatus.value();
        this.status = this.getHttpMessage(httpStatus);
    }

    private String getHttpMessage(HttpStatus httpStatus) {
        if (httpStatus.is2xxSuccessful()) {
            return "success";
        } else if (httpStatus.is4xxClientError()) {
            return "error";
        } else if (httpStatus.is5xxServerError()) {
            return "fail";
        } else {
            return "unknown";
        }
    }
}
