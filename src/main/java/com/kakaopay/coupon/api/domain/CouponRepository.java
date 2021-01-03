package com.kakaopay.coupon.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
  Coupon findByCode(String code);

  List<Coupon> findAllByUserNo(Long userNo);

  List<Coupon> findAllByExpirationDateBeforeAndStatus(LocalDate localDate, Status status);

  List<Coupon> findAllByExpirationDateAndStatus(LocalDate localDate, Status status);
}
