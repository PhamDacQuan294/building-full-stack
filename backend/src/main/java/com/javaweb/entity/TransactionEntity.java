package com.javaweb.entity;

import com.javaweb.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "transactions")
@SQLDelete(sql = "UPDATE transactions SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class TransactionEntity extends BaseEntity {

  @Column(name = "note", length = 255)
  private String note;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customerid", nullable = false)
  private CustomerEntity customer;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 50)
  private TransactionType type;
}
