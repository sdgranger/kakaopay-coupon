package com.kakaopay.coupon.auth.controller;

import com.kakaopay.coupon.auth.service.UserDetailService;
import com.kakaopay.coupon.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final UserDetailService userDetailService;

  @PostMapping("/api/auth/signUp")
  public ApiResponse<String> signUp(SignUpRequestDto requestDto){
    String token = userDetailService.signUp(requestDto.getId(), requestDto.getPassword());

    return ApiResponse.ok(token);
  }

  @PostMapping("/api/auth/signIn")
  public ApiResponse<String> signIn(SignInRequestDto requestDto){
    String token = userDetailService.signIn(requestDto.getId(), requestDto.getPassword());

    return ApiResponse.ok(token);
  }
}
