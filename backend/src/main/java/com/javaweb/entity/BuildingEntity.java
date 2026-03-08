package com.javaweb.entity;

import com.javaweb.enums.District;
import com.javaweb.enums.RentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "building")
@SQLDelete(sql = "UPDATE building SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class BuildingEntity extends BaseEntity{
  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "street", length = 255)
  private String street;

  @Column(name = "ward", length = 255)
  private String ward;

  @Enumerated(EnumType.STRING)
  @Column(name = "district", nullable = false, length = 50)
  private District district;

  @Column(name = "structure", length = 255)
  private String structure;

  @Column(name = "numberofbasement")
  private Integer numberOfBasement;

  @Column(name = "floorarea")
  private Integer floorArea;

  @Column(name = "direction", length = 255)
  private String direction;

  @Column(name = "level", length = 255)
  private String level;

  @Column(name = "rentprice")
  private Integer rentPrice;

  @Lob
  @Column(name = "rentpricedescription")
  private String rentPriceDescription;

  @Column(name = "servicefee", length = 255)
  private String serviceFee;

  @Column(name = "carfee", length = 255)
  private String carFee;

  @Column(name = "motorbikefee", length = 255)
  private String motorbikeFee;

  @Column(name = "overtimefee", length = 255)
  private String overtimeFee;

  @Column(name = "waterfee", length = 255)
  private String waterFee;

  @Column(name = "electricityfee", length = 255)
  private String electricityFee;

  @Column(name = "deposit", length = 255)
  private String deposit;

  @Column(name = "payment", length = 255)
  private String payment;

  @Column(name = "renttime", length = 255)
  private String rentTime;

  @Column(name = "decorationtime", length = 255)
  private String decorationTime;

  @Column(name = "brokeragefee", precision = 13, scale = 2)
  private BigDecimal brokerageFee;

  @Column(name = "note", length = 255)
  private String note;

  @Column(name = "linkofbuilding", length = 255)
  private String linkOfBuilding;

  @Column(name = "map", length = 255)
  private String map;

  @Column(name = "image", length = 255)
  private String image;

  @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RentAreaEntity> rentAreas = new HashSet<>();

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "building_renttype", joinColumns = @JoinColumn(name = "buildingid"))
  @Enumerated(EnumType.STRING)
  @Column(name = "renttype", nullable = false, length = 50)
  private Set<RentType> rentTypes = new HashSet<>();
}
