package com.kakaopay.coupon.auth.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignUpRequestDto {
    private String id;
    private String password;
}
