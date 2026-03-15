package com.javaweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "`user`")
@SQLDelete(sql = "UPDATE `user` SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class UserEntity extends BaseEntity implements UserDetails {
  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String username;

  // BCrypt thường 60 ký tự
  @JsonIgnore
  @Column(name = "password", nullable = false, length = 60)
  private String password;

  @Column(name = "fullname", length = 150)
  private String fullname;

  @Column(name = "phone", length = 20)
  private String phone;

  @Column(name = "email", length = 120)
  private String email;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "user_role",
    joinColumns = @JoinColumn(name = "userid"),
    inverseJoinColumns = @JoinColumn(name = "roleid")
  )
  private Set<RoleEntity> roles = new HashSet<>();

  @OneToMany(mappedBy = "staff")
  private Set<CustomerCareEntity> customerCares = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (RoleEntity role : roles) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));

      for (PermissionEntity permission : role.getPermissions()) {
        authorities.add(new SimpleGrantedAuthority(permission.getCode()));
      }
    }

    return authorities;
  }

  @Override
  public String getUsername() {

    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
