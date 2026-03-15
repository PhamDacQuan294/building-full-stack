package com.javaweb.entity;

import com.javaweb.enums.CustomerStatus;
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

  @Column(name = "companyname")
  private String companyName;

  @Column(name = "demand", columnDefinition = "TEXT")
  private String demand;

  @Enumerated(EnumType.STRING)
  @Column(name = "customer_status")
  private CustomerStatus customerStatus;

  @Column(name = "note", columnDefinition = "TEXT")
  private String note;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<AssignmentCustomerEntity> assignmentCustomers = new HashSet<>();

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CustomerCareEntity> customerCares = new HashSet<>();
}