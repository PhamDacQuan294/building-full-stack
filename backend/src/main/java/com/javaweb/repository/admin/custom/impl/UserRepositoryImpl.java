package com.javaweb.repository.admin.custom.impl;

import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.user.UserSearchRequestDTO;
import com.javaweb.repository.admin.custom.UserRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<UserEntity> findAll(UserSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT DISTINCT u.*
            FROM user u
            WHERE u.deleted = 0
        """);

    appendWhere(request, sql);
    sql.append(" ORDER BY u.id DESC ");

    Query query = entityManager.createNativeQuery(sql.toString(), UserEntity.class);
    setParams(request, query);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 10 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public long countTotalItems(UserSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT COUNT(DISTINCT u.id)
            FROM user u
            WHERE u.deleted = 0
        """);

    appendWhere(request, sql);

    Query query = entityManager.createNativeQuery(sql.toString());
    setParams(request, query);

    Number total = (Number) query.getSingleResult();
    return total.longValue();
  }

  private void appendWhere(UserSearchRequestDTO request, StringBuilder sql) {
    if (request.getFullName() != null && !request.getFullName().isBlank()) {
      sql.append(" AND u.fullname LIKE :fullName ");
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      sql.append(" AND u.email LIKE :email ");
    }

    if (request.getPhone() != null && !request.getPhone().isBlank()) {
      sql.append(" AND u.phone LIKE :phone ");
    }

    if (request.getStatus() != null && !request.getStatus().isBlank()) {
      sql.append(" AND u.status = :status ");
    }
  }

  private void setParams(UserSearchRequestDTO request, Query query) {
    if (request.getFullName() != null && !request.getFullName().isBlank()) {
      query.setParameter("fullName", "%" + request.getFullName().trim() + "%");
    }

    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      query.setParameter("email", "%" + request.getEmail().trim() + "%");
    }

    if (request.getPhone() != null && !request.getPhone().isBlank()) {
      query.setParameter("phone", "%" + request.getPhone().trim() + "%");
    }

    if (request.getStatus() != null && !request.getStatus().isBlank()) {
      query.setParameter("status", request.getStatus().trim());
    }
  }
}