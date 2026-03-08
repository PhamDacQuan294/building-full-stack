package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "rentarea")
@SQLDelete(sql = "UPDATE rentarea SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class RentAreaEntity extends BaseEntity {

  @Column(name = "value")
  private Integer value;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "buildingid", nullable = false)
  private BuildingEntity building;
}
