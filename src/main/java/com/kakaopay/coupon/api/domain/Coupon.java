package com.kakaopay.coupon.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Coupon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code", length = 17, nullable = false)
  private String code;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private LocalDateTime publishedAt;

  private LocalDateTime usedAt;

  private LocalDateTime expirationAt;

  @Enumerated(value = EnumType.STRING)
  private Status status;

  @Builder
  public Coupon(String code, LocalDateTime publishedAt, LocalDateTime usedAt,
                LocalDateTime expirationAt, Status status) {
    this.code = code;
    this.publishedAt = publishedAt;
    this.usedAt = usedAt;
    this.expirationAt = expirationAt;
    this.status = status;
  }
}
