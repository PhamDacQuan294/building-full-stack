package com.javaweb.repository.admin.custom.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;
import com.javaweb.repository.admin.custom.BuildingRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  public static void joinTable(BuildingSearchRequestDTO params, StringBuilder sql) {
    Long staffId = params.getStaffId();
    if (staffId != null) {
      sql.append(" INNER JOIN assignmentbuilding ab ON b.id = ab.buildingid ");
    }

    List<String> types = params.getTypes();
    if (types != null && !types.isEmpty()) {
      sql.append(" INNER JOIN building_renttype br ON b.id = br.buildingid ");
    }
  }

  public static void queryNomal(BuildingSearchRequestDTO params, StringBuilder where) {
    try {
      Field[] fields = BuildingSearchRequestDTO.class.getDeclaredFields();

      for (Field item : fields) {
        item.setAccessible(true);
        String fieldName = item.getName();

        if (!fieldName.equals("staffId")
          && !fieldName.equals("types")
          && !fieldName.startsWith("area")
          && !fieldName.startsWith("rentPrice")) {

          Object value = item.get(params);

          if (value != null) {
            String typeName = item.getType().getName();

            if (typeName.equals("java.lang.Long") || typeName.equals("java.lang.Integer")) {
              where.append(" AND b.").append(fieldName).append(" = ").append(value);
            } else if (typeName.equals("java.lang.String")) {
              where.append(" AND b.").append(fieldName).append(" LIKE '%").append(value).append("%' ");
            }
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void querySpecial(BuildingSearchRequestDTO params, StringBuilder where) {
    Long staffId = params.getStaffId();
    if (staffId != null) {
      where.append(" AND ab.staffid = ").append(staffId);
    }

    Long rentAreaTo = params.getAreaTo();
    Long rentAreaFrom = params.getAreaFrom();

    if (rentAreaFrom != null || rentAreaTo != null) {
      where.append(" AND EXISTS (SELECT 1 FROM rentarea r WHERE b.id = r.buildingid ");

      if (rentAreaFrom != null) {
        where.append(" AND r.value >= ").append(rentAreaFrom);
      }

      if (rentAreaTo != null) {
        where.append(" AND r.value <= ").append(rentAreaTo);
      }

      where.append(") ");
    }

    Long rentPriceTo = params.getRentPriceTo();
    Long rentPriceFrom = params.getRentPriceFrom();

    if (rentPriceFrom != null) {
      where.append(" AND b.rentprice >= ").append(rentPriceFrom);
    }

    if (rentPriceTo != null) {
      where.append(" AND b.rentprice <= ").append(rentPriceTo);
    }

    List<String> types = params.getTypes();
    if (types != null && !types.isEmpty()) {
      where.append(" AND (");
      String sql = types.stream()
        .map(it -> " br.renttype = '" + it + "' ")
        .collect(Collectors.joining(" OR "));
      where.append(sql);
      where.append(") ");
    }
  }

  @Override
  public List<BuildingEntity> findAll(BuildingSearchRequestDTO params) {
    StringBuilder sql = new StringBuilder("SELECT DISTINCT b.* FROM building b ");

    joinTable(params, sql);

    StringBuilder where = new StringBuilder(" WHERE 1=1 ");
    queryNomal(params, where);
    querySpecial(params, where);

    sql.append(where);

    Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
    return query.getResultList();
  }
}