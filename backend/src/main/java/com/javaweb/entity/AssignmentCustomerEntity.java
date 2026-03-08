package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "assignmentcustomer")
@SQLDelete(sql = "UPDATE assignmentcustomer SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class AssignmentCustomerEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "staffid", nullable = false)
  private UserEntity staff;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customerid", nullable = false)
  private CustomerEntity customer;
}