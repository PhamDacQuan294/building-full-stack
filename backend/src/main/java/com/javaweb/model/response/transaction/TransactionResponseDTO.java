package com.javaweb.model.response.transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDTO {
  private Long id;
  private Long customerId;
  private String customerName;
  private Long staffId;
  private String staffName;
  private String transactionType;
  private String transactionStatus;
  private String content;
  private String transactionDate;
}