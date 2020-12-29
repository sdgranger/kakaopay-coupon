package com.kakaopay.coupon.common.config;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

          if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return errorAttributes;
          }
        }

        errorAttributes.put("message", "Error processing the request");

        if (error instanceof EntityNotFoundException) {
          errorAttributes.put("errors", ((EntityNotFoundException) error).getMessage());
        }

        return errorAttributes;
      }

    };
  }
}
