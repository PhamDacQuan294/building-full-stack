package com.javaweb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "`user`")
@SQLDelete(sql = "UPDATE `user` SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class UserEntity extends BaseEntity{
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
}
