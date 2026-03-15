package com.javaweb.repository.admin.custom.impl;

import com.javaweb.model.request.notification.EmailLogSearchRequestDTO;
import com.javaweb.model.response.notification.EmailLogResponseDTO;
import com.javaweb.repository.admin.custom.EmailLogRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmailLogRepositoryImpl implements EmailLogRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<EmailLogResponseDTO> search(EmailLogSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT el.id, el.actor_id, el.receiver_id, el.to_email, el.subject,
                   el.content, el.mail_type, el.module, el.object_id, el.sent_success,
                   el.error_message, el.createddate,
                   a.email as actor_email, a.fullname as actor_name,
                   r.email as receiver_email, r.fullname as receiver_name
            FROM email_log el
            LEFT JOIN user a ON el.actor_id = a.id
            LEFT JOIN user r ON el.receiver_id = r.id
            WHERE el.deleted = 0
        """);

    appendWhere(request, sql);
    sql.append(" ORDER BY el.createddate DESC ");

    Query query = entityManager.createNativeQuery(sql.toString());
    setParams(request, query);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 10 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    List<Object[]> rows = query.getResultList();
    List<EmailLogResponseDTO> result = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    for (Object[] row : rows) {
      EmailLogResponseDTO item = new EmailLogResponseDTO();

      item.setId(((Number) row[0]).longValue());

      if (row[1] != null) item.setActorId(((Number) row[1]).longValue());
      if (row[2] != null) item.setReceiverId(((Number) row[2]).longValue());

      item.setToEmail((String) row[3]);
      item.setSubject((String) row[4]);
      item.setContent((String) row[5]);
      item.setMailType((String) row[6]);
      item.setModule((String) row[7]);

      if (row[8] != null) item.setObjectId(((Number) row[8]).longValue());

      item.setSentSuccess(row[9] != null && ((Boolean) row[9]));
      item.setErrorMessage((String) row[10]);

      Object createdDateObj = row[11];
      if (createdDateObj instanceof Timestamp timestamp) {
        item.setCreatedDate(sdf.format(timestamp));
      } else if (createdDateObj instanceof java.util.Date date) {
        item.setCreatedDate(sdf.format(date));
      } else if (createdDateObj != null) {
        item.setCreatedDate(createdDateObj.toString());
      } else {
        item.setCreatedDate("");
      }

      item.setActorEmail((String) row[12]);
      item.setActorName((String) row[13]);
      item.setReceiverEmail((String) row[14]);
      item.setReceiverName((String) row[15]);

      result.add(item);
    }

    return result;
  }

  @Override
  public long count(EmailLogSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*)
            FROM email_log el
            WHERE el.deleted = 0
        """);

    appendWhere(request, sql);

    Query query = entityManager.createNativeQuery(sql.toString());
    setParams(request, query);

    Number total = (Number) query.getSingleResult();
    return total.longValue();
  }

  private void appendWhere(EmailLogSearchRequestDTO request, StringBuilder sql) {
    if (request.getToEmail() != null && !request.getToEmail().isBlank()) {
      sql.append(" AND el.to_email LIKE :toEmail ");
    }

    if (request.getMailType() != null && !request.getMailType().isBlank()) {
      sql.append(" AND el.mail_type = :mailType ");
    }

    if (request.getModule() != null && !request.getModule().isBlank()) {
      sql.append(" AND el.module = :module ");
    }
  }

  private void setParams(EmailLogSearchRequestDTO request, Query query) {
    if (request.getToEmail() != null && !request.getToEmail().isBlank()) {
      query.setParameter("toEmail", "%" + request.getToEmail().trim() + "%");
    }

    if (request.getMailType() != null && !request.getMailType().isBlank()) {
      query.setParameter("mailType", request.getMailType());
    }

    if (request.getModule() != null && !request.getModule().isBlank()) {
      query.setParameter("module", request.getModule());
    }
  }
}