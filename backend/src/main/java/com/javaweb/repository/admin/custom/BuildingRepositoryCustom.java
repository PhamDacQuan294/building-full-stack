package com.javaweb.repository.admin.custom;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.building.BuildingSearchRequestDTO;

import java.util.List;

public interface BuildingRepositoryCustom {
  List<BuildingEntity> findAll(BuildingSearchRequestDTO params);
}
