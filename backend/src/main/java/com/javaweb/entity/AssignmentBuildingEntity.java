package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "assignmentbuilding")
@SQLDelete(sql = "UPDATE assignmentbuilding SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class AssignmentBuildingEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "staffid", nullable = false)
  private UserEntity staff;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "buildingid", nullable = false)
  private BuildingEntity building;
}
