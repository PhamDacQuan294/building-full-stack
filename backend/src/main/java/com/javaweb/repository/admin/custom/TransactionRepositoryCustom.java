package com.javaweb.repository.admin.custom;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.request.transaction.TransactionSearchRequestDTO;

import java.util.List;

public interface TransactionRepositoryCustom {
  List<TransactionEntity> findAll(TransactionSearchRequestDTO request);
  int countTotalItems(TransactionSearchRequestDTO request);
}