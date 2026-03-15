package com.javaweb.converter;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.response.transaction.TransactionDetailResponseDTO;
import com.javaweb.model.response.transaction.TransactionResponseDTO;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class TransactionDTOConverter {

  public TransactionResponseDTO toTransactionResponseDTO(TransactionEntity entity) {
    TransactionResponseDTO dto = new TransactionResponseDTO();
    dto.setId(entity.getId());
    dto.setCustomerId(entity.getCustomer().getId());
    dto.setCustomerName(entity.getCustomer().getFullname());
    dto.setStaffId(entity.getStaff().getId());
    dto.setStaffName(entity.getStaff().getFullname());
    dto.setTransactionType(entity.getTransactionType() != null ? entity.getTransactionType().name() : null);
    dto.setTransactionStatus(entity.getTransactionStatus() != null ? entity.getTransactionStatus().name() : null);
    dto.setContent(entity.getContent());

    if (entity.getTransactionDate() != null) {
      dto.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entity.getTransactionDate()));
    }

    return dto;
  }

  public TransactionDetailResponseDTO toTransactionDetailResponseDTO(TransactionEntity entity) {
    TransactionDetailResponseDTO dto = new TransactionDetailResponseDTO();
    dto.setId(entity.getId());
    dto.setCustomerId(entity.getCustomer().getId());
    dto.setStaffId(entity.getStaff().getId());
    dto.setTransactionType(entity.getTransactionType() != null ? entity.getTransactionType().name() : null);
    dto.setTransactionStatus(entity.getTransactionStatus() != null ? entity.getTransactionStatus().name() : null);
    dto.setContent(entity.getContent());

    if (entity.getTransactionDate() != null) {
      dto.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(entity.getTransactionDate()));
    }

    return dto;
  }
}