package com.javaweb.model.response.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetailResponseDTO {
  private Long id;
  private Long customerId;
  private Long staffId;
  private String transactionType;
  private String transactionStatus;
  private String content;
  private String transactionDate;
}