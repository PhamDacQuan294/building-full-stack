package com.javaweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "email_log")
@SQLDelete(sql = "UPDATE email_log SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class EmailLogEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "actor_id")
  private UserEntity actor;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private UserEntity receiver;

  @Column(name = "to_email")
  private String toEmail;

  @Column(name = "subject")
  private String subject;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "mail_type")
  private String mailType;

  @Column(name = "module")
  private String module;

  @Column(name = "object_id")
  private Long objectId;

  @Column(name = "sent_success")
  private Boolean sentSuccess;

  @Column(name = "error_message")
  private String errorMessage;
}