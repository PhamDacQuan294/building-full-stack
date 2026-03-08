package com.javaweb.enums;

public enum CommonStatus {
  ACTIVE("Hoạt động"),
  INACTIVE("Ngưng hoạt động");

  private final String label;

  CommonStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}