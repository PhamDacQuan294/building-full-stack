package com.javaweb.repository.client.custom.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.client.request.building.ClientBuildingSearchRequestDTO;
import com.javaweb.model.client.request.home.HomeSearchRequestDTO;
import com.javaweb.model.client.response.building.HighlightDistrictDTO;
import com.javaweb.repository.client.custom.ClientBuildingRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientBuildingRepositoryImpl implements ClientBuildingRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<BuildingEntity> findFeaturedBuildings() {
    String sql = """
            SELECT b.*
            FROM building b
            WHERE b.deleted = 0
              AND b.status = 'ACTIVE'
            ORDER BY b.rentprice DESC, b.id DESC
            LIMIT 6
        """;

    Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
    return query.getResultList();
  }

  @Override
  public List<BuildingEntity> findNewestBuildings() {
    String sql = """
            SELECT b.*
            FROM building b
            WHERE b.deleted = 0
              AND b.status = 'ACTIVE'
            ORDER BY b.createddate DESC, b.id DESC
            LIMIT 8
        """;

    Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
    return query.getResultList();
  }


  @Override
  public List<BuildingEntity> searchBuildings(HomeSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT b.*
            FROM building b
            WHERE b.deleted = 0
              AND b.status = 'ACTIVE'
        """);

    appendSearchWhere(request, sql);

    sql.append(" ORDER BY b.id DESC ");

    Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
    setSearchParams(request, query);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 8 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public long countSearchBuildings(HomeSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
            SELECT COUNT(b.id)
            FROM building b
            WHERE b.deleted = 0
              AND b.status = 'ACTIVE'
        """);

    appendSearchWhere(request, sql);

    Query query = entityManager.createNativeQuery(sql.toString());
    setSearchParams(request, query);

    Number total = (Number) query.getSingleResult();
    return total.longValue();
  }

  @Override
  public List<HighlightDistrictDTO> getHighlightDistricts() {
    String sql = """
            SELECT b.district, COUNT(b.id)
            FROM building b
            WHERE b.deleted = 0
              AND b.status = 'ACTIVE'
              AND b.district IS NOT NULL
              AND b.district <> ''
            GROUP BY b.district
            ORDER BY COUNT(b.id) DESC
            LIMIT 6
        """;

    Query query = entityManager.createNativeQuery(sql);
    List<Object[]> rows = query.getResultList();

    List<HighlightDistrictDTO> result = new ArrayList<>();
    for (Object[] row : rows) {
      HighlightDistrictDTO dto = new HighlightDistrictDTO();
      dto.setCode((String) row[0]);
      dto.setName((String) row[0]);
      dto.setTotalBuildings(((Number) row[1]).longValue());
      result.add(dto);
    }

    return result;
  }

  @Override
  public List<String> getAvailableDistricts() {
    String sql = """
            SELECT DISTINCT b.district
            FROM building b
            WHERE b.deleted = 0
              AND b.status = 'ACTIVE'
              AND b.district IS NOT NULL
              AND b.district <> ''
            ORDER BY b.district ASC
        """;

    Query query = entityManager.createNativeQuery(sql);
    return query.getResultList();
  }

  private void appendSearchWhere(HomeSearchRequestDTO request, StringBuilder sql) {
    if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
      sql.append(" AND (b.name LIKE :keyword OR b.street LIKE :keyword OR b.ward LIKE :keyword) ");
    }

    if (request.getDistrict() != null && !request.getDistrict().isBlank()) {
      sql.append(" AND b.district = :district ");
    }

    if (request.getRentPriceFrom() != null) {
      sql.append(" AND b.rentprice >= :rentPriceFrom ");
    }

    if (request.getRentPriceTo() != null) {
      sql.append(" AND b.rentprice <= :rentPriceTo ");
    }
  }

  private void setSearchParams(HomeSearchRequestDTO request, Query query) {
    if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
      query.setParameter("keyword", "%" + request.getKeyword().trim() + "%");
    }

    if (request.getDistrict() != null && !request.getDistrict().isBlank()) {
      query.setParameter("district", request.getDistrict().trim());
    }

    if (request.getRentPriceFrom() != null) {
      query.setParameter("rentPriceFrom", request.getRentPriceFrom());
    }

    if (request.getRentPriceTo() != null) {
      query.setParameter("rentPriceTo", request.getRentPriceTo());
    }
  }

  @Override
  public List<BuildingEntity> findClientBuildings(ClientBuildingSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
      SELECT b.*
      FROM building b
      WHERE b.deleted = 0
        AND b.status = 'ACTIVE'
    """);

    appendWhere(request, sql);
    appendSort(request, sql);

    Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
    setParams(request, query);

    int page = request.getPage() == null || request.getPage() <= 0 ? 1 : request.getPage();
    int limit = request.getLimit() == null || request.getLimit() <= 0 ? 8 : request.getLimit();

    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public long countClientBuildings(ClientBuildingSearchRequestDTO request) {
    StringBuilder sql = new StringBuilder("""
      SELECT COUNT(b.id)
      FROM building b
      WHERE b.deleted = 0
        AND b.status = 'ACTIVE'
    """);

    appendWhere(request, sql);

    Query query = entityManager.createNativeQuery(sql.toString());
    setParams(request, query);

    Number result = (Number) query.getSingleResult();
    return result.longValue();
  }

  @Override
  public Optional<BuildingEntity> findClientBuildingDetail(Long id) {
    String sql = """
      SELECT b.*
      FROM building b
      WHERE b.deleted = 0
        AND b.status = 'ACTIVE'
        AND b.id = :id
      LIMIT 1
    """;

    Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
    query.setParameter("id", id);

    List<BuildingEntity> result = query.getResultList();
    if (result.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(result.get(0));
  }

  private void appendWhere(ClientBuildingSearchRequestDTO request, StringBuilder sql) {
    if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
      sql.append("""
        AND (
          b.name LIKE :keyword
          OR b.street LIKE :keyword
          OR b.ward LIKE :keyword
          OR b.district LIKE :keyword
        )
      """);
    }

    if (request.getDistrict() != null && !request.getDistrict().isBlank()) {
      sql.append(" AND b.district = :district ");
    }

    if (request.getRentPriceFrom() != null) {
      sql.append(" AND b.rentprice >= :rentPriceFrom ");
    }

    if (request.getRentPriceTo() != null) {
      sql.append(" AND b.rentprice <= :rentPriceTo ");
    }

    if (request.getAreaFrom() != null) {
      sql.append(" AND b.floorarea >= :areaFrom ");
    }

    if (request.getAreaTo() != null) {
      sql.append(" AND b.floorarea <= :areaTo ");
    }

    if (request.getRentType() != null && !request.getRentType().isBlank()) {
      sql.append(" AND b.type LIKE :rentType ");
    }
  }

  private void appendSort(ClientBuildingSearchRequestDTO request, StringBuilder sql) {
    String sortBy = request.getSortBy();

    if ("priceAsc".equals(sortBy)) {
      sql.append(" ORDER BY b.rentprice ASC, b.id DESC ");
    } else if ("priceDesc".equals(sortBy)) {
      sql.append(" ORDER BY b.rentprice DESC, b.id DESC ");
    } else if ("areaAsc".equals(sortBy)) {
      sql.append(" ORDER BY b.floorarea ASC, b.id DESC ");
    } else if ("areaDesc".equals(sortBy)) {
      sql.append(" ORDER BY b.floorarea DESC, b.id DESC ");
    } else {
      sql.append(" ORDER BY b.createddate DESC, b.id DESC ");
    }
  }

  private void setParams(ClientBuildingSearchRequestDTO request, Query query) {
    if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
      query.setParameter("keyword", "%" + request.getKeyword().trim() + "%");
    }

    if (request.getDistrict() != null && !request.getDistrict().isBlank()) {
      query.setParameter("district", request.getDistrict().trim());
    }

    if (request.getRentPriceFrom() != null) {
      query.setParameter("rentPriceFrom", request.getRentPriceFrom());
    }

    if (request.getRentPriceTo() != null) {
      query.setParameter("rentPriceTo", request.getRentPriceTo());
    }

    if (request.getAreaFrom() != null) {
      query.setParameter("areaFrom", request.getAreaFrom());
    }

    if (request.getAreaTo() != null) {
      query.setParameter("areaTo", request.getAreaTo());
    }

    if (request.getRentType() != null && !request.getRentType().isBlank()) {
      query.setParameter("rentType", "%" + request.getRentType().trim() + "%");
    }
  }
}