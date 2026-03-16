package com.javaweb.repository.client;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.repository.client.custom.ClientBuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientBuildingRepository extends JpaRepository<BuildingEntity, Long>, ClientBuildingRepositoryCustom{
}