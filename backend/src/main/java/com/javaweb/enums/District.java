package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum District {

  QUAN_1("Quận 1"),
  QUAN_3("Quận 3"),
  QUAN_5("Quận 5");

  private final String districtName;

  District(String districtName) {
    this.districtName = districtName;
  }

  public String getDistrictName() {
    return districtName;
  }

  public static Map<String, String> type() {
    Map<String, String> districts = new TreeMap<>();
    for (District it : District.values()) {
      districts.put(it.name(), it.getDistrictName());
    }
    return districts;
  }
}