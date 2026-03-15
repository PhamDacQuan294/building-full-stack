package com.javaweb.repository.admin.custom.impl;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.request.transaction.TransactionSearchRequestDTO;
import com.javaweb.repository.admin.custom.TransactionRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<TransactionEntity> findAll(TransactionSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("SELECT t.* FROM transactions t WHERE 1=1 ");

    if (request.getCustomerId() != null) {
      sql.append(" AND t.customerid = ").append(request.getCustomerId());
    }

    if (request.getStaffId() != null) {
      sql.append(" AND t.staffid = ").append(request.getStaffId());
    }

    if (request.getTransactionType() != null) {
      sql.append(" AND t.transaction_type = '")
        .append(request.getTransactionType().name())
        .append("' ");
    }

    if (request.getTransactionStatus() != null) {
      sql.append(" AND t.transaction_status = '")
        .append(request.getTransactionStatus().name())
        .append("' ");
    }

    if (request.getFromDate() != null && !request.getFromDate().isBlank()) {
      sql.append(" AND DATE(t.transaction_date) >= '")
        .append(request.getFromDate())
        .append("' ");
    }

    if (request.getToDate() != null && !request.getToDate().isBlank()) {
      sql.append(" AND DATE(t.transaction_date) <= '")
        .append(request.getToDate())
        .append("' ");
    }

    sql.append(" ORDER BY t.id DESC ");

    Query query = entityManager.createNativeQuery(sql.toString(), TransactionEntity.class);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 10 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public int countTotalItems(TransactionSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(t.id) FROM transactions t WHERE 1=1 ");

    if (request.getCustomerId() != null) {
      sql.append(" AND t.customerid = ").append(request.getCustomerId());
    }

    if (request.getStaffId() != null) {
      sql.append(" AND t.staffid = ").append(request.getStaffId());
    }

    if (request.getTransactionType() != null) {
      sql.append(" AND t.transaction_type = '")
        .append(request.getTransactionType().name())
        .append("' ");
    }

    if (request.getTransactionStatus() != null) {
      sql.append(" AND t.transaction_status = '")
        .append(request.getTransactionStatus().name())
        .append("' ");
    }

    if (request.getFromDate() != null && !request.getFromDate().isBlank()) {
      sql.append(" AND DATE(t.transaction_date) >= '")
        .append(request.getFromDate())
        .append("' ");
    }

    if (request.getToDate() != null && !request.getToDate().isBlank()) {
      sql.append(" AND DATE(t.transaction_date) <= '")
        .append(request.getToDate())
        .append("' ");
    }

    Query query = entityManager.createNativeQuery(sql.toString());
    Number result = (Number) query.getSingleResult();
    return result.intValue();
  }
}