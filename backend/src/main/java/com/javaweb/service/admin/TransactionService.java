package com.javaweb.service.admin;

import com.javaweb.model.request.transaction.TransactionRequestDTO;
import com.javaweb.model.request.transaction.TransactionSearchRequestDTO;
import com.javaweb.model.response.transaction.TransactionDetailResponseDTO;
import com.javaweb.model.response.transaction.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
  List<TransactionResponseDTO> findAll(TransactionSearchRequestDTO request);
  int countTotalItems(TransactionSearchRequestDTO request);
  void createTransaction(TransactionRequestDTO request);
  void updateTransaction(Long id, TransactionRequestDTO request);
  void deleteTransaction(Long id);
  TransactionDetailResponseDTO getTransactionDetail(Long id);
  void updateTransactionStatus(Long id, String status);
}