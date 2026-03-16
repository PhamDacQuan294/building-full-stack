package com.javaweb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(
  name = "favorite_building",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"customer_id", "building_id"})
  }
)
@SQLDelete(sql = "UPDATE favorite_building SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class FavoriteBuildingEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "building_id", nullable = false)
  private BuildingEntity building;
}