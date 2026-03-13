package com.javaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
}
