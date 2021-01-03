package com.kakaopay.coupon.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserDetails {

  private static final long serialVersionUID = 6396079419309274853L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long no;

  private String id;

  private String password;

  @ElementCollection
  private List<String> roles = new ArrayList<>();

  public User(String id, String password) {
    this.id = id;
    this.password = password;
  }

  public void grantRole(String role){
    roles.add(role);
  }

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (String role : roles) {
      authorities.add(new SimpleGrantedAuthority(role));
    }
    return authorities;
  }

  @Override
  public String getUsername() {
    return this.id;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
