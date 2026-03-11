package com.javaweb.model.response.building;

import com.javaweb.model.response.AbstractResponseDTO;
import com.javaweb.model.response.user.StaffResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingResponseDTO extends AbstractResponseDTO {
  private String name;
  private String address;
  private Long numberOfBasement;
  private String managerName;
  private String managerPhone;
  private Long floorArea;
  private String rentArea;
  private String emptyArea;
  private Long rentPrice;
  private String serviceFee;
  private Double brokerageFee;
  private String status;
  private String imageUrl;
}
