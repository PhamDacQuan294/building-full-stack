package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum TransactionType {
  CSKH("Chăm sóc khách hàng"),
  DAN_XEM("Dẫn xem"),
  DAT_COC("Đặt cọc"),
  TU_VAN("Tư vấn"),
  KY_HOP_DONG("Ký hợp đồng"),
  DAN_DI_XEM("Dẫn đi xem");

  private final String transactionTypeName;

  TransactionType(String transactionTypeName) {
    this.transactionTypeName = transactionTypeName;
  }

  public String getTransactionTypeName() {
    return transactionTypeName;
  }

  public static Map<String, String> type() {
    Map<String, String> types = new TreeMap<>();
    for (TransactionType it : TransactionType.values()) {
      types.put(it.name(), it.getTransactionTypeName());
    }
    return types;
  }
}
