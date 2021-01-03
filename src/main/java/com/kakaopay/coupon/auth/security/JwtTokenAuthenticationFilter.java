package com.kakaopay.coupon.auth.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private AuthenticationManager authenticationManager;

  public JwtTokenAuthenticationFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
    List<AuthenticationProvider> list = new ArrayList<>();
    list.add(jwtAuthenticationProvider);

    ProviderManager providerManager = new ProviderManager(list);
    providerManager.setEraseCredentialsAfterAuthentication(false);

    authenticationManager = providerManager;
  }

  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.getContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);

    chain.doFilter(request, response);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    String extractToken;
    String tokenHeader = request.getHeader("Authorization");
    if (tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
      extractToken = null;
    } else {
      extractToken = tokenHeader.replaceAll("Bearer ", "");
    }
    log.debug("request token header: " + extractToken);

    if (extractToken != null) {
      try {
        Authentication authResult = authenticationManager.authenticate(new JwtAuthenticationToken(extractToken));
        successfulAuthentication(request, response, filterChain, authResult);
      } catch (JwtExpiredTokenException e) {
        filterChain.doFilter(request, response);
      } catch (Exception e) {
        filterChain.doFilter(request, response);
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
