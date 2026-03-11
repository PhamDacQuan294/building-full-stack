package com.javaweb.model.request.building;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BuildingRequestDTO {
  private String name;
  private String district;
  private String ward;
  private String street;
  private Integer numberOfBasement;
  private String floorArea;
  private String rentPrice;
  private String status;
  private String managerName;
  private String managerPhone;
  private String imageUrl;
}
