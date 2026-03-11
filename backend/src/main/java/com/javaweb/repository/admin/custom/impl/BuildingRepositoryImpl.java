package com.javaweb.repository.admin.custom.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.repository.admin.custom.BuildingRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<BuildingEntity> findAll(BuildingSearchRequestDTO params, Pageable pageable) {
    StringBuilder sql = new StringBuilder("SELECT DISTINCT b.* FROM building b ");
    sql.append(buildJoinQuery(params));
    sql.append(" WHERE b.deleted = 0 ");
    sql.append(buildWhereQuery(params));
    sql.append(buildSortQuery(params));

    Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
    setQueryParams(query, params);

    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    return query.getResultList();
  }

  @Override
  public int countTotalItem(BuildingSearchRequestDTO params) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT b.id) FROM building b ");
    sql.append(buildJoinQuery(params));
    sql.append(" WHERE b.deleted = 0 ");
    sql.append(buildWhereQuery(params));

    Query query = entityManager.createNativeQuery(sql.toString());
    setQueryParams(query, params);

    return ((Number) query.getSingleResult()).intValue();
  }

  private String buildJoinQuery(BuildingSearchRequestDTO params) {
    StringBuilder sql = new StringBuilder();

    if (params.getStaffId() != null) {
      sql.append(" INNER JOIN assignmentbuilding ab ON ab.buildingid = b.id ");
    }

    if (params.getTypes() != null && !params.getTypes().isEmpty()) {
      sql.append(" INNER JOIN building_renttype br ON br.buildingid = b.id ");
    }

    return sql.toString();
  }

  private String buildWhereQuery(BuildingSearchRequestDTO params) {
    StringBuilder sql = new StringBuilder();

    if (hasText(params.getName())) {
      sql.append(" AND b.name LIKE :name ");
    }

    if (hasText(params.getDistrict())) {
      sql.append(" AND b.district = :district ");
    }

    if (hasText(params.getWard())) {
      sql.append(" AND b.ward LIKE :ward ");
    }

    if (hasText(params.getStreet())) {
      sql.append(" AND b.street LIKE :street ");
    }

    if (params.getFloorArea() != null) {
      sql.append(" AND b.floorarea = :floorArea ");
    }

    if (params.getNumberOfBasement() != null) {
      sql.append(" AND b.numberofbasement = :numberOfBasement ");
    }

    if (params.getRentPriceFrom() != null) {
      sql.append(" AND b.rentprice >= :rentPriceFrom ");
    }

    if (params.getRentPriceTo() != null) {
      sql.append(" AND b.rentprice <= :rentPriceTo ");
    }

    if (params.getStaffId() != null) {
      sql.append(" AND ab.staffid = :staffId ");
    }

    if (params.getStatus() != null) {
      sql.append(" AND b.status = :status ");
    }

    if (params.getAreaFrom() != null || params.getAreaTo() != null) {
      sql.append(" AND EXISTS (");
      sql.append("   SELECT 1 FROM rentarea r ");
      sql.append("   WHERE r.buildingid = b.id AND r.deleted = 0 ");

      if (params.getAreaFrom() != null) {
        sql.append(" AND r.value >= :areaFrom ");
      }

      if (params.getAreaTo() != null) {
        sql.append(" AND r.value <= :areaTo ");
      }

      sql.append(" ) ");
    }

    if (params.getTypes() != null && !params.getTypes().isEmpty()) {
      sql.append(" AND br.renttype IN :types ");
    }

    return sql.toString();
  }

  private String buildSortQuery(BuildingSearchRequestDTO params) {
    if (!hasText(params.getSort())) {
      return " ORDER BY b.id DESC ";
    }

    return switch (params.getSort()) {
      case "name-asc" -> " ORDER BY b.name ASC ";
      case "name-desc" -> " ORDER BY b.name DESC ";
      case "rent-asc" -> " ORDER BY b.rentprice ASC ";
      case "rent-desc" -> " ORDER BY b.rentprice DESC ";
      case "area-asc" -> " ORDER BY b.floorarea ASC ";
      case "area-desc" -> " ORDER BY b.floorarea DESC ";
      default -> " ORDER BY b.id DESC ";
    };
  }

  private void setQueryParams(Query query, BuildingSearchRequestDTO params) {
    if (hasText(params.getName())) {
      query.setParameter("name", "%" + params.getName().trim() + "%");
    }

    if (hasText(params.getDistrict())) {
      query.setParameter("district", params.getDistrict().trim());
    }

    if (hasText(params.getWard())) {
      query.setParameter("ward", "%" + params.getWard().trim() + "%");
    }

    if (hasText(params.getStreet())) {
      query.setParameter("street", "%" + params.getStreet().trim() + "%");
    }

    if (params.getFloorArea() != null) {
      query.setParameter("floorArea", params.getFloorArea());
    }

    if (params.getNumberOfBasement() != null) {
      query.setParameter("numberOfBasement", params.getNumberOfBasement());
    }

    if (params.getRentPriceFrom() != null) {
      query.setParameter("rentPriceFrom", params.getRentPriceFrom());
    }

    if (params.getRentPriceTo() != null) {
      query.setParameter("rentPriceTo", params.getRentPriceTo());
    }

    if (params.getStaffId() != null) {
      query.setParameter("staffId", params.getStaffId());
    }

    if (params.getStatus() != null) {
      query.setParameter("status", params.getStatus().name());
    }

    if (params.getAreaFrom() != null) {
      query.setParameter("areaFrom", params.getAreaFrom());
    }

    if (params.getAreaTo() != null) {
      query.setParameter("areaTo", params.getAreaTo());
    }

    if (params.getTypes() != null && !params.getTypes().isEmpty()) {
      query.setParameter("types", params.getTypes());
    }
  }

  private boolean hasText(String value) {
    return value != null && !value.trim().isEmpty();
  }
}