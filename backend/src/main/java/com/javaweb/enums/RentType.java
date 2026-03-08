package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum RentType {
  TANG_TRET("Tầng trệt"),
  NGUYEN_CAN("Nguyên căn"),
  NOI_THAT("Nội thất");

  private final String rentTypeName;

  RentType(String rentTypeName) {
    this.rentTypeName = rentTypeName;
  }

  public String getRentTypeName() {
    return rentTypeName;
  }

  public static Map<String, String> type() {
    Map<String, String> rentTypes = new TreeMap<>();
    for (RentType it : RentType.values()) {
      rentTypes.put(it.name(), it.getRentTypeName());
    }
    return rentTypes;
  }
}