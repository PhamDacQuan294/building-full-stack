package com.javaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "customer")
@SQLDelete(sql = "UPDATE customer SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class CustomerEntity extends BaseEntity {

  @Column(name = "fullname", nullable = false, length = 150)
  private String fullname;

  @Column(name = "phone", length = 20)
  private String phone;

  @Column(name = "email", length = 120)
  private String email;
}