package com.javaweb.model.request.transaction;

import com.javaweb.enums.TransactionStatus;
import com.javaweb.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSearchRequestDTO {
  private Long customerId;
  private Long staffId;
  private TransactionType transactionType;
  private TransactionStatus transactionStatus;
  private String fromDate;
  private String toDate;

  private Integer page = 1;
  private Integer limit = 10;
}