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
@Table(name = "activity_log")
@SQLDelete(sql = "UPDATE activity_log SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class ActivityLogEntity extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "actor_id")
  private UserEntity actor;

  @Column(name = "actor_email")
  private String actorEmail;

  @Column(name = "actor_name")
  private String actorName;

  @Column(name = "action")
  private String action;

  @Column(name = "module")
  private String module;

  @Column(name = "description")
  private String description;

  @Column(name = "object_id")
  private Long objectId;
}