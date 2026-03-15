package com.javaweb.model.response.dashboard;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DashboardResponseDTO {
  private Long totalUsers;
  private Long totalRoles;
  private Long totalBuildings;
  private Long totalCustomers;
  private Long totalTransactions;

  private Long activeBuildings;
  private Long inactiveBuildings;

  private Long newCustomersThisMonth;

  private List<ChartItemDTO> transactionStatusStats;
  private List<ChartItemDTO> customerStatusStats;
  private List<ChartItemDTO> monthlyTransactionStats;
}