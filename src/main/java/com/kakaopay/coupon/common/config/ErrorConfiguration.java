package com.kakaopay.coupon.common.config;

import com.kakaopay.coupon.common.exception.CustomException;
import com.kakaopay.coupon.common.exception.InValidStatusException;
import com.kakaopay.coupon.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Configuration
public class ErrorConfiguration {

  @Bean
  public ErrorAttributes errorAttributes() {
    return new DefaultErrorAttributes() {

      @Override
      public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                    ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
        Throwable error = getError(webRequest);

        if (webRequest instanceof ServletWebRequest) {
          ServletWebRequest servletWebRequest = (ServletWebRequest) webRequest;
          HttpServletRequest servletRequest = servletWebRequest.getRequest();
          Integer statusCode = (Integer) servletRequest.getAttribute("javax.servlet.error.status_code");
          errorAttributes.put("status", statusCode);
        }

        log.error("error", error);
        errorAttributes.put("message", "error processing the request");

        if (error instanceof NotFoundException) {
          errorAttributes.put("code", "E404");
        }
        else if (error instanceof CustomException) {
          errorAttributes.put("code", ((CustomException) error).getErrorCode());
        }

        return errorAttributes;
      }

    };
  }
}
