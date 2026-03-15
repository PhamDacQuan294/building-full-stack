package com.javaweb.service.admin.impl;

//import com.javaweb.enums.Status;
import com.javaweb.model.response.dashboard.ChartItemDTO;
import com.javaweb.model.response.dashboard.DashboardResponseDTO;
import com.javaweb.repository.admin.BuildingRepository;
import com.javaweb.repository.admin.CustomerRepository;
import com.javaweb.repository.admin.RoleRepository;
import com.javaweb.repository.admin.TransactionRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.DashboardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private BuildingRepository buildingRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public DashboardResponseDTO getDashboardData() {
    DashboardResponseDTO dto = new DashboardResponseDTO();

    dto.setTotalUsers(userRepository.count());
    dto.setTotalRoles(roleRepository.count());
    dto.setTotalBuildings(buildingRepository.count());
    dto.setTotalCustomers(customerRepository.count());
    dto.setTotalTransactions(transactionRepository.count());

    dto.setActiveBuildings(countBuildingsByStatus("ACTIVE"));
    dto.setInactiveBuildings(countBuildingsByStatus("INACTIVE"));

    dto.setNewCustomersThisMonth(countNewCustomersThisMonth());

    dto.setTransactionStatusStats(getTransactionStatusStats());
    dto.setCustomerStatusStats(getCustomerStatusStats());
    dto.setMonthlyTransactionStats(getMonthlyTransactionStats());

    return dto;
  }

  private Long countBuildingsByStatus(String status) {
    String sql = "SELECT COUNT(*) FROM building b WHERE b.deleted = 0 AND b.status = :status";
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("status", status);

    Number result = (Number) query.getSingleResult();
    return result.longValue();
  }

  private Long countNewCustomersThisMonth() {
    LocalDate firstDay = LocalDate.now().withDayOfMonth(1);

    String sql = "SELECT COUNT(*) FROM customer c WHERE c.deleted = 0 AND DATE(c.createddate) >= :firstDay";
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("firstDay", firstDay.toString());

    Number result = (Number) query.getSingleResult();
    return result.longValue();
  }

  private List<ChartItemDTO> getTransactionStatusStats() {
    String sql = """
            SELECT t.transaction_status, COUNT(*) 
            FROM transactions t
            WHERE t.deleted = 0
            GROUP BY t.transaction_status
        """;

    Query query = entityManager.createNativeQuery(sql);
    List<Object[]> rows = query.getResultList();

    List<ChartItemDTO> result = new ArrayList<>();
    for (Object[] row : rows) {
      String name = row[0] != null ? row[0].toString() : "UNKNOWN";
      Long value = ((Number) row[1]).longValue();
      result.add(new ChartItemDTO(name, value));
    }

    return result;
  }

  private List<ChartItemDTO> getCustomerStatusStats() {
    String sql = """
            SELECT c.customer_status, COUNT(*) 
            FROM customer c
            WHERE c.deleted = 0
            GROUP BY c.customer_status
        """;

    Query query = entityManager.createNativeQuery(sql);
    List<Object[]> rows = query.getResultList();

    List<ChartItemDTO> result = new ArrayList<>();
    for (Object[] row : rows) {
      String name = row[0] != null ? row[0].toString() : "UNKNOWN";
      Long value = ((Number) row[1]).longValue();
      result.add(new ChartItemDTO(name, value));
    }

    return result;
  }

  private List<ChartItemDTO> getMonthlyTransactionStats() {
    String sql = """
            SELECT MONTH(t.transaction_date) AS month_value, COUNT(*) 
            FROM transactions t
            WHERE t.deleted = 0
              AND YEAR(t.transaction_date) = YEAR(CURDATE())
            GROUP BY MONTH(t.transaction_date)
            ORDER BY MONTH(t.transaction_date)
        """;

    Query query = entityManager.createNativeQuery(sql);
    List<Object[]> rows = query.getResultList();

    List<ChartItemDTO> result = new ArrayList<>();
    for (Object[] row : rows) {
      String name = "Tháng " + row[0].toString();
      Long value = ((Number) row[1]).longValue();
      result.add(new ChartItemDTO(name, value));
    }

    return result;
  }
}