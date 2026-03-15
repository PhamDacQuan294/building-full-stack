package com.javaweb.service.admin.impl;

import com.javaweb.converter.CustomerDTOConverter;
import com.javaweb.entity.AssignmentCustomerEntity;
import com.javaweb.entity.CustomerCareEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.enums.CommonStatus;
import com.javaweb.enums.CustomerStatus;
import com.javaweb.model.request.customer.AssignmentCustomerRequestDTO;
import com.javaweb.model.request.customer.CustomerCareRequestDTO;
import com.javaweb.model.request.customer.CustomerRequestDTO;
import com.javaweb.model.request.customer.CustomerSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.customer.CustomerCareResponseDTO;
import com.javaweb.model.response.customer.CustomerDetailResponseDTO;
import com.javaweb.model.response.customer.CustomerResponseDTO;
import com.javaweb.model.response.customer.StaffAssignmentResponseDTO;
import com.javaweb.model.response.user.StaffResponseDTO;
import com.javaweb.repository.admin.AssignmentCustomerRepository;
import com.javaweb.repository.admin.CustomerCareRepository;
import com.javaweb.repository.admin.CustomerRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.repository.admin.custom.CustomerRepositoryCustom;
import com.javaweb.service.admin.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepositoryCustom customerRepositoryCustom;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerDTOConverter customerDTOConverter;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AssignmentCustomerRepository assignmentCustomerRepository;

  @Autowired
  private CustomerCareRepository customerCareRepository;

  @Override
  public List<CustomerResponseDTO> findAll(CustomerSearchRequestDTO request) {
    List<CustomerEntity> customers = customerRepositoryCustom.findAll(request);
    List<CustomerResponseDTO> result = new ArrayList<>();

    for (CustomerEntity customer : customers) {
      result.add(customerDTOConverter.toCustomerResponseDTO(customer));
    }

    return result;
  }

  @Override
  public int countTotalItems(CustomerSearchRequestDTO request) {
    return customerRepositoryCustom.countTotalItems(request);
  }

  @Override
  public void createCustomer(CustomerRequestDTO request) {
    CustomerEntity customer = customerDTOConverter.toCustomerEntity(request);

    if (customer.getCustomerStatus() == null) {
      customer.setCustomerStatus(CustomerStatus.MOI);
    }

    customerRepository.save(customer);
  }

  @Override
  public void updateCustomer(Long id, CustomerRequestDTO request) {
    CustomerEntity customer = customerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    customerDTOConverter.updateCustomerEntity(request, customer);
    customerRepository.save(customer);
  }

  @Override
  public void deleteCustomer(Long id) {
    CustomerEntity customer = customerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    customerRepository.delete(customer);
  }

  @Override
  public CustomerDetailResponseDTO getCustomerDetail(Long id) {
    CustomerEntity customer = customerRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    return customerDTOConverter.toCustomerDetailResponseDTO(customer);
  }

  @Override
  public ResponseDTO<?> listStaffs(Long customerId) {
    CustomerEntity customer = customerRepository.findById(customerId)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(CommonStatus.ACTIVE, "STAFF");
    List<AssignmentCustomerEntity> assignments = assignmentCustomerRepository.findByCustomer_Id(customer.getId());

    List<Long> assignedStaffIds = new ArrayList<>();
    for (AssignmentCustomerEntity item : assignments) {
      assignedStaffIds.add(item.getStaff().getId());
    }

    List<StaffResponseDTO> staffResponseDTOs = new ArrayList<>();
    for (UserEntity staff : staffs) {
      StaffResponseDTO dto = new StaffResponseDTO();
      dto.setStaffId(staff.getId());
      dto.setFullName(staff.getFullname());
      dto.setChecked(assignedStaffIds.contains(staff.getId()) ? "checked" : "");
      staffResponseDTOs.add(dto);
    }

    ResponseDTO<List<StaffResponseDTO>> response = new ResponseDTO<>();
    response.setData(staffResponseDTOs);
    response.setMessage("success");
    response.setDetail("Load staffs success");
    return response;
  }

  @Override
  public void updateAssignmentCustomer(AssignmentCustomerRequestDTO request) {
    CustomerEntity customer = customerRepository.findById(request.getCustomerId())
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    assignmentCustomerRepository.deleteByCustomer_Id(customer.getId());

    if (request.getStaffs() == null || request.getStaffs().isEmpty()) {
      return;
    }

    for (Long staffId : request.getStaffs()) {
      UserEntity staff = userRepository.findById(staffId)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy staff"));

      AssignmentCustomerEntity assignment = new AssignmentCustomerEntity();
      assignment.setCustomer(customer);
      assignment.setStaff(staff);

      assignmentCustomerRepository.save(assignment);
    }
  }

  @Override
  public List<StaffAssignmentResponseDTO> getStaffAssignments(Long customerId) {
    CustomerEntity customer = customerRepository.findById(customerId)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(CommonStatus.ACTIVE, "STAFF");
    List<AssignmentCustomerEntity> assignments = assignmentCustomerRepository.findByCustomer_Id(customer.getId());

    List<Long> assignedStaffIds = new ArrayList<>();
    for (AssignmentCustomerEntity item : assignments) {
      assignedStaffIds.add(item.getStaff().getId());
    }

    List<StaffAssignmentResponseDTO> result = new ArrayList<>();
    for (UserEntity staff : staffs) {
      StaffAssignmentResponseDTO dto = new StaffAssignmentResponseDTO();
      dto.setStaffId(staff.getId());
      dto.setFullName(staff.getFullname());
      dto.setChecked(assignedStaffIds.contains(staff.getId()));
      result.add(dto);
    }

    return result;
  }

  @Override
  public List<CustomerCareResponseDTO> getCareHistory(Long customerId) {
    List<CustomerCareEntity> items = customerCareRepository.findByCustomer_IdOrderByCareDateDesc(customerId);

    List<CustomerCareResponseDTO> result = new ArrayList<>();
    for (CustomerCareEntity item : items) {
      CustomerCareResponseDTO dto = new CustomerCareResponseDTO();
      dto.setId(item.getId());
      dto.setStaffName(item.getStaff().getFullname());
      dto.setContent(item.getContent());
      dto.setStatusAfterCare(item.getStatusAfterCare() != null ? item.getStatusAfterCare().name() : null);
      dto.setCareDate(item.getCareDate());
      result.add(dto);
    }

    return result;
  }

  @Override
  public void createCareHistory(CustomerCareRequestDTO request) {
    CustomerEntity customer = customerRepository.findById(request.getCustomerId())
      .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    UserEntity staff = userRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy staff đang đăng nhập"));

    CustomerCareEntity entity = new CustomerCareEntity();
    entity.setCustomer(customer);
    entity.setStaff(staff);
    entity.setContent(request.getContent());
    entity.setStatusAfterCare(request.getStatusAfterCare());
    entity.setCareDate(new Date());

    customerCareRepository.save(entity);

    if (request.getStatusAfterCare() != null) {
      customer.setCustomerStatus(request.getStatusAfterCare());
      customerRepository.save(customer);
    }
  }
}