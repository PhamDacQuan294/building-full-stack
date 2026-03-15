package com.javaweb.repository.admin.custom.impl;

import com.javaweb.model.request.activitylog.ActivityLogSearchRequestDTO;
import com.javaweb.model.response.activitylog.ActivityLogResponseDTO;
import com.javaweb.repository.admin.custom.ActivityLogRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActivityLogRepositoryImpl implements ActivityLogRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<ActivityLogResponseDTO> search(ActivityLogSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT al.id, al.actor_id, al.actor_email, al.actor_name,
                   al.action, al.module, al.description, al.object_id, al.createddate
            FROM activity_log al
            WHERE al.deleted = 0
        """);

    appendWhere(request, sql);
    sql.append(" ORDER BY al.createddate DESC ");

    Query query = entityManager.createNativeQuery(sql.toString());
    setParams(request, query);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 10 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    List<Object[]> rows = query.getResultList();
    List<ActivityLogResponseDTO> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    for (Object[] row : rows) {
      ActivityLogResponseDTO item = new ActivityLogResponseDTO();

      item.setId(((Number) row[0]).longValue());

      if (row[1] != null) {
        item.setActorId(((Number) row[1]).longValue());
      }

      item.setActorEmail((String) row[2]);
      item.setActorName((String) row[3]);
      item.setAction((String) row[4]);
      item.setModule((String) row[5]);
      item.setDescription((String) row[6]);

      if (row[7] != null) {
        item.setObjectId(((Number) row[7]).longValue());
      }

      Object createdDateObj = row[8];
      if (createdDateObj instanceof Timestamp timestamp) {
        item.setCreatedDate(sdf.format(timestamp));
      } else if (createdDateObj instanceof java.util.Date date) {
        item.setCreatedDate(sdf.format(date));
      } else if (createdDateObj != null) {
        item.setCreatedDate(createdDateObj.toString());
      } else {
        item.setCreatedDate("");
      }

      result.add(item);
    }

    return result;
  }

  @Override
  public long count(ActivityLogSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*)
            FROM activity_log al
            WHERE al.deleted = 0
        """);

    appendWhere(request, sql);

    Query query = entityManager.createNativeQuery(sql.toString());
    setParams(request, query);

    Number total = (Number) query.getSingleResult();
    return total.longValue();
  }

  private void appendWhere(ActivityLogSearchRequestDTO request, StringBuilder sql) {
    if (request.getActorEmail() != null && !request.getActorEmail().isBlank()) {
      sql.append(" AND al.actor_email LIKE :actorEmail ");
    }

    if (request.getAction() != null && !request.getAction().isBlank()) {
      sql.append(" AND al.action = :action ");
    }

    if (request.getModule() != null && !request.getModule().isBlank()) {
      sql.append(" AND al.module = :module ");
    }
  }

  private void setParams(ActivityLogSearchRequestDTO request, Query query) {
    if (request.getActorEmail() != null && !request.getActorEmail().isBlank()) {
      query.setParameter("actorEmail", "%" + request.getActorEmail().trim() + "%");
    }

    if (request.getAction() != null && !request.getAction().isBlank()) {
      query.setParameter("action", request.getAction());
    }

    if (request.getModule() != null && !request.getModule().isBlank()) {
      query.setParameter("module", request.getModule());
    }
  }
}