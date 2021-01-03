package com.kakaopay.coupon.api.domain;

import com.kakaopay.coupon.common.exception.InValidStatusException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Coupon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  @Column(name = "code", length = 17, nullable = false)
  private String code;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private LocalDateTime publishedAt;

  private LocalDate expirationDate;

  private LocalDateTime usedAt;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  private Long userNo;

  @Builder
  public Coupon(String code, LocalDateTime usedAt, Status status) {
    this.code = code;
    this.usedAt = usedAt;
    this.status = status;
  }

  public static Coupon create(String code) {
    return Coupon.builder().code(code).status(Status.CREATED).build();
  }

  public void publish(Long userNo) {
    this.userNo = userNo;
    status = Status.PUBLISHED;
    publishedAt = LocalDateTime.now();
    expirationDate = publishedAt.plusMonths(1).toLocalDate();
  }

  public boolean expired(){
    if(status == Status.PUBLISHED && expirationDate.isBefore(LocalDate.now())){
      return true;
    }

    return false;
  }

  public void use() {
    if(status != Status.PUBLISHED){
      throw new InValidStatusException("can't use coupon");
    }
    usedAt = LocalDateTime.now();
    status = Status.USED;
  }

  public void cancel() {
    if(status != Status.USED){
      throw new InValidStatusException("can't cancel coupon");
    }
      status = Status.PUBLISHED;
  }

  public Long getNo() {
    return no;
  }

  public String getCode() {
    return code;
  }

  public Status currentStatus() {
    if(expired()){
      return Status.EXPIRED;
    }
    return status;
  }

  public Long getUserNo() {
    return userNo;
  }

  public LocalDateTime getPublishedAt() {
    return publishedAt;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public LocalDateTime getUsedAt() {
    return usedAt;
  }
}
