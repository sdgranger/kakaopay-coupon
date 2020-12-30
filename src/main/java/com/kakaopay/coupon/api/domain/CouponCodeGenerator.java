package com.kakaopay.coupon.api.domain;

import java.util.Random;
import java.util.UUID;

public class CouponCodeGenerator {
  public String gen() {
    long l = System.currentTimeMillis();
    String key = Long.toHexString(l);
    String randomString = UUID.randomUUID().toString().replaceAll("-", "");
    StringBuilder sb = new StringBuilder(key);

    Random random = new Random();
    for (int i = 0; i < 4; i++) {
      int ran = random.nextInt(randomString.length());
      sb.append(randomString.charAt(ran));
    }
    String genKey = sb.toString();
    String hashCode = getHash(genKey);

    return sb.append(hashCode).toString();
  }

  private String getHash(String key) {
    int sum = 0;
    for (char c : key.toCharArray()) {
      int i = Integer.parseInt(String.valueOf(c), 16);
      sum += i;
    }
    return Long.toHexString(sum);
  }

  public boolean isValid(String code) {
    String key = code.substring(0, 15);
    String extractCode = code.substring(15);

    String hashCode = getHash(key);

    return extractCode.equals(hashCode);
  }
}
