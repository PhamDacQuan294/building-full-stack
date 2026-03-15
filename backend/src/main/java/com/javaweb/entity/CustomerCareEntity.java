package com.javaweb.entity;

import com.javaweb.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "customer_care")
public class CustomerCareEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "customerid", nullable = false)
  private CustomerEntity customer;

  @ManyToOne
  @JoinColumn(name = "staffid", nullable = false)
  private UserEntity staff;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "status_after_care")
  private CustomerStatus statusAfterCare;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "care_date")
  private Date careDate;
}