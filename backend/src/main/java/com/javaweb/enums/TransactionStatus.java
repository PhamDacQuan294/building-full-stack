package com.javaweb.enums;

public enum TransactionStatus {
  MOI("Mới"),
  DANG_XU_LY("Đang xử lý"),
  HOAN_THANH("Hoàn thành"),
  HUY("Hủy");

  private final String label;

  TransactionStatus(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}