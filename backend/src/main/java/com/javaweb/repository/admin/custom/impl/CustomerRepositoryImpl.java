package com.javaweb.repository.admin.custom.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.request.customer.CustomerSearchRequestDTO;
import com.javaweb.repository.admin.custom.CustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<CustomerEntity> findAll(CustomerSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("SELECT DISTINCT c.* FROM customer c ");
    StringBuilder where = new StringBuilder(" WHERE 1=1 ");

    if (request.getStaffId() != null) {
      sql.append(" INNER JOIN assignmentcustomer ac ON c.id = ac.customerid ");
      where.append(" AND ac.staffid = ").append(request.getStaffId());
    }

    if (request.getFullName() != null && !request.getFullName().isBlank()) {
      where.append(" AND c.fullname LIKE '%").append(request.getFullName()).append("%' ");
    }

    if (request.getPhone() != null && !request.getPhone().isBlank()) {
      where.append(" AND c.phone LIKE '%").append(request.getPhone()).append("%' ");
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      where.append(" AND c.email LIKE '%").append(request.getEmail()).append("%' ");
    }

    if (request.getStatus() != null) {
      where.append(" AND c.customer_status = '").append(request.getStatus().name()).append("' ");
    }

    sql.append(where);
    sql.append(" ORDER BY c.id DESC ");

    Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 10 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public int countTotalItems(CustomerSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT c.id) FROM customer c ");
    StringBuilder where = new StringBuilder(" WHERE 1=1 ");

    if (request.getStaffId() != null) {
      sql.append(" INNER JOIN assignmentcustomer ac ON c.id = ac.customerid ");
      where.append(" AND ac.staffid = ").append(request.getStaffId());
    }

    if (request.getFullName() != null && !request.getFullName().isBlank()) {
      where.append(" AND c.fullname LIKE '%").append(request.getFullName()).append("%' ");
    }

    if (request.getPhone() != null && !request.getPhone().isBlank()) {
      where.append(" AND c.phone LIKE '%").append(request.getPhone()).append("%' ");
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      where.append(" AND c.email LIKE '%").append(request.getEmail()).append("%' ");
    }

    if (request.getStatus() != null) {
      where.append(" AND c.customer_status = '").append(request.getStatus().name()).append("' ");
    }

    sql.append(where);

    Query query = entityManager.createNativeQuery(sql.toString());
    Number result = (Number) query.getSingleResult();

    return result.intValue();
  }
}