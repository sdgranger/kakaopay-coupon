package com.kakaopay.coupon.batch;

import com.kakaopay.coupon.api.domain.Coupon;
import com.kakaopay.coupon.api.domain.CouponRepository;
import com.kakaopay.coupon.api.domain.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExpirationNotificationBatch {
  private final CouponRepository couponRepository;

  @Scheduled(cron = "0 0 10 * * ?")
  public void notificationBefore3days(){
    List<Coupon> coupons = couponRepository.findAllByExpirationDateAndStatus(
            LocalDate.now().plusDays(3), Status.PUBLISHED);

    for (Coupon coupon : coupons) {
      log.info("[" + coupon.getUserNo() + "]쿠폰이 3일후 만료됩니다.");
    }
  }
}
