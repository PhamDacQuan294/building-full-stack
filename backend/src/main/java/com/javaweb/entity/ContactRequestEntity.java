package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "contact_request")
@SQLDelete(sql = "UPDATE contact_request SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class ContactRequestEntity extends BaseEntity {

  @Column(name = "full_name", nullable = false, length = 255)
  private String fullName;

  @Column(name = "phone", nullable = false, length = 20)
  private String phone;

  @Column(name = "email", length = 255)
  private String email;

  @Lob
  @Column(name = "message")
  private String message;

  @Column(name = "request_status", nullable = false, length = 50)
  private String requestStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "building_id")
  private BuildingEntity building;
}