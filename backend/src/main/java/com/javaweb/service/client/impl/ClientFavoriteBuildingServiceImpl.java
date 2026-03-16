package com.javaweb.service.client.impl;

import com.javaweb.converter.client.ClientFavoriteBuildingConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.FavoriteBuildingEntity;
import com.javaweb.model.client.response.favorite.ClientFavoriteBuildingDTO;
import com.javaweb.model.client.response.favorite.ClientFavoriteToggleResponseDTO;
import com.javaweb.repository.client.ClientBuildingRepository;
import com.javaweb.repository.client.ClientCustomerRepository;
import com.javaweb.repository.client.ClientFavoriteBuildingRepository;
import com.javaweb.service.client.ClientFavoriteBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientFavoriteBuildingServiceImpl implements ClientFavoriteBuildingService {

  @Autowired
  private ClientFavoriteBuildingRepository favoriteBuildingRepository;

  @Autowired
  private ClientCustomerRepository customerRepository;

  @Autowired
  private ClientBuildingRepository buildingRepository;

  @Autowired
  private ClientFavoriteBuildingConverter favoriteBuildingConverter;

  @Override
  public ClientFavoriteToggleResponseDTO addFavorite(String email, Long buildingId) {
    CustomerEntity customer = getCustomerByEmail(email);
    BuildingEntity building = getBuildingById(buildingId);

    if (!favoriteBuildingRepository.existsByCustomerIdAndBuildingIdAndDeletedFalse(customer.getId(), building.getId())) {
      FavoriteBuildingEntity entity = new FavoriteBuildingEntity();
      entity.setCustomer(customer);
      entity.setBuilding(building);
      favoriteBuildingRepository.save(entity);
    }

    ClientFavoriteToggleResponseDTO dto = new ClientFavoriteToggleResponseDTO();
    dto.setBuildingId(buildingId);
    dto.setFavorite(true);
    return dto;
  }

  @Override
  public ClientFavoriteToggleResponseDTO removeFavorite(String email, Long buildingId) {
    CustomerEntity customer = getCustomerByEmail(email);

    favoriteBuildingRepository
      .findByCustomerIdAndBuildingIdAndDeletedFalse(customer.getId(), buildingId)
      .ifPresent(favoriteBuildingRepository::delete);

    ClientFavoriteToggleResponseDTO dto = new ClientFavoriteToggleResponseDTO();
    dto.setBuildingId(buildingId);
    dto.setFavorite(false);
    return dto;
  }

  @Override
  public ClientFavoriteToggleResponseDTO checkFavorite(String email, Long buildingId) {
    CustomerEntity customer = getCustomerByEmail(email);

    boolean isFavorite = favoriteBuildingRepository.existsByCustomerIdAndBuildingIdAndDeletedFalse(
      customer.getId(),
      buildingId
    );

    ClientFavoriteToggleResponseDTO dto = new ClientFavoriteToggleResponseDTO();
    dto.setBuildingId(buildingId);
    dto.setFavorite(isFavorite);
    return dto;
  }

  @Override
  public List<ClientFavoriteBuildingDTO> getMyFavorites(String email) {
    CustomerEntity customer = getCustomerByEmail(email);

    List<FavoriteBuildingEntity> entities = favoriteBuildingRepository
      .findByCustomerIdAndDeletedFalseOrderByIdDesc(customer.getId());

    List<ClientFavoriteBuildingDTO> result = new ArrayList<>();
    for (FavoriteBuildingEntity entity : entities) {
      result.add(favoriteBuildingConverter.toDTO(entity));
    }

    return result;
  }

  private CustomerEntity getCustomerByEmail(String email) {
    return customerRepository.findByEmailAndDeletedFalse(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
  }

  private BuildingEntity getBuildingById(Long buildingId) {
    return buildingRepository.findById(buildingId)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy bất động sản"));
  }
}