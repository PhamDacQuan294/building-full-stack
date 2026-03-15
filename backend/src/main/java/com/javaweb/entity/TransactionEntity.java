package com.javaweb.entity;

import com.javaweb.enums.TransactionStatus;
import com.javaweb.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Date;

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

  @ManyToOne
  @JoinColumn(name = "staffid", nullable = false)
  private UserEntity staff;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type", nullable = false)
  private TransactionType transactionType;

  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_status", nullable = false)
  private TransactionStatus transactionStatus;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "transaction_date")
  private Date transactionDate;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "type", nullable = false, length = 50)
//  private TransactionType type;
}
