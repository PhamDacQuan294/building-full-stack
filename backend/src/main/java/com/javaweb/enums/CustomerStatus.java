package com.javaweb.enums;

public enum CustomerStatus {
  MOI("Mới"),
  DANG_CHAM_SOC("Đang chăm sóc"),
  DA_CHOT("Đã chốt"),
  NGUNG_THEO_DOI("Ngừng theo dõi");

  private final String label;

  CustomerStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}