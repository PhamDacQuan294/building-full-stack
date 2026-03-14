package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "permission")
@SQLDelete(sql = "UPDATE permission SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class PermissionEntity extends BaseEntity {

  @Column(name = "code", nullable = false, unique = true, length = 100)
  private String code;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "description", length = 255)
  private String description;
}