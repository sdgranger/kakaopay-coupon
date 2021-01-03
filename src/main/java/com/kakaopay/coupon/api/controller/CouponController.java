package com.kakaopay.coupon.api.controller;

import com.kakaopay.coupon.api.controller.dto.PublishRequestDto;
import com.kakaopay.coupon.api.controller.dto.PublishResponseDto;
import com.kakaopay.coupon.api.controller.dto.StatusUpdateRequestDto;
import com.kakaopay.coupon.api.service.CouponCommandService;
import com.kakaopay.coupon.api.service.CouponCreateService;
import com.kakaopay.coupon.api.service.CouponFindService;
import com.kakaopay.coupon.api.service.dto.CouponDto;
import com.kakaopay.coupon.api.service.dto.UserCouponDto;
import com.kakaopay.coupon.common.exception.InValidRequestException;
import com.kakaopay.coupon.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponController {
  private final CouponCreateService couponCreateService;
  private final CouponCommandService couponCommandService;
  private final CouponFindService couponFindService;

  @PostMapping("/api/coupon")
  public ApiResponse<Boolean> create(int count){
    couponCreateService.create(count);

    return ApiResponse.ok(true);
  }

  @GetMapping("/api/coupon/users/{userNo}")
  public ApiResponse<List<UserCouponDto>> find(@PathVariable Long userNo){
    List<UserCouponDto> userCoupons = couponFindService.find(userNo);

    return ApiResponse.ok(userCoupons);
  }

  @PutMapping("/api/coupon/publication")
  public ApiResponse<PublishResponseDto> publish(PublishRequestDto dto){
    CouponDto coupon = couponCommandService.publish(dto.getCouponCode(), dto.getUserNo());

    return ApiResponse.ok(new PublishResponseDto(coupon.getCode()));
  }

  @PutMapping("/api/coupon/status")
  public ApiResponse use(StatusUpdateRequestDto dto){
    CouponDto useCoupon;
    switch (dto.getStatus()){
      case USED:
        useCoupon = couponCommandService.use(dto.getCouponCode());
        break;
      case PUBLISHED:
        useCoupon = couponCommandService.cancel(dto.getCouponCode());
        break;
      default:
        throw new InValidRequestException("invalid request status");
    }

    return ApiResponse.ok(useCoupon.getCode());
  }
}
