package com.kakaopay.coupon.common.config;

import com.kakaopay.coupon.auth.security.JwtAuthenticationProvider;
import com.kakaopay.coupon.auth.security.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private JwtAuthenticationProvider jwtAuthenticationProvider;

  protected void configure(HttpSecurity security) throws Exception {
    security.httpBasic().disable()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/api/coupon").permitAll()
            .antMatchers("/api/coupon/publication").hasAuthority("ROLE_USER")
            .antMatchers("/api/coupon/users/{userNo}",
                    "/api/coupon/status/{status}").hasAuthority("ROLE_USER")
            .anyRequest()
            .permitAll()
            .and()
            .headers()
            .frameOptions().disable()
            .and()
            .addFilterBefore(new JwtTokenAuthenticationFilter(jwtAuthenticationProvider), ExceptionTranslationFilter.class);;
  }
}
