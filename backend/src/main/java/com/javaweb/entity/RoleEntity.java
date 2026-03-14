package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@SQLDelete(sql = "UPDATE role SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
public class RoleEntity extends BaseEntity {
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "code", nullable = false, unique = true, length = 50)
  private String code;

  @Column(name = "description", length = 255)
  private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "role_permission",
    joinColumns = @JoinColumn(name = "roleid"),
    inverseJoinColumns = @JoinColumn(name = "permissionid")
  )
  private Set<PermissionEntity> permissions = new HashSet<>();
}
