package com.javaweb.entity;

import com.javaweb.enums.CommonStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "createddate")
  @CreatedDate
  private Date createdDate;

  @Column(name = "createdby")
  @CreatedBy
  private String createdBy;

  @Column(name = "modifieddate")
  @LastModifiedDate
  private Date modifiedDate;

  @Column(name = "modifiedby")
  @LastModifiedBy
  private String modifiedBy;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = Boolean.FALSE;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private CommonStatus status = CommonStatus.ACTIVE;
}
