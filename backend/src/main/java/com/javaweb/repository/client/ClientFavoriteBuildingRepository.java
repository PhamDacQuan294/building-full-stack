package com.javaweb.repository.client;

import com.javaweb.entity.FavoriteBuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientFavoriteBuildingRepository extends JpaRepository<FavoriteBuildingEntity, Long> {
  Optional<FavoriteBuildingEntity> findByCustomerIdAndBuildingIdAndDeletedFalse(Long customerId, Long buildingId);
  boolean existsByCustomerIdAndBuildingIdAndDeletedFalse(Long customerId, Long buildingId);
  List<FavoriteBuildingEntity> findByCustomerIdAndDeletedFalseOrderByIdDesc(Long customerId);
}