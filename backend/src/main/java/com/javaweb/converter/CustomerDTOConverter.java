package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.request.customer.CustomerRequestDTO;
import com.javaweb.model.response.customer.CustomerDetailResponseDTO;
import com.javaweb.model.response.customer.CustomerResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOConverter {

  public CustomerEntity toCustomerEntity(CustomerRequestDTO request) {
    CustomerEntity customer = new CustomerEntity();
    customer.setFullname(request.getFullName());
    customer.setPhone(request.getPhone());
    customer.setEmail(request.getEmail());
    customer.setCompanyName(request.getCompanyName());
    customer.setDemand(request.getDemand());
    customer.setCustomerStatus(request.getStatus());
    customer.setNote(request.getNote());
    return customer;
  }

  public void updateCustomerEntity(CustomerRequestDTO request, CustomerEntity customer) {
    customer.setFullname(request.getFullName());
    customer.setPhone(request.getPhone());
    customer.setEmail(request.getEmail());
    customer.setCompanyName(request.getCompanyName());
    customer.setDemand(request.getDemand());
    customer.setCustomerStatus(request.getStatus());
    customer.setNote(request.getNote());
  }

  public CustomerResponseDTO toCustomerResponseDTO(CustomerEntity customer) {
    CustomerResponseDTO dto = new CustomerResponseDTO();
    dto.setId(customer.getId());
    dto.setFullName(customer.getFullname());
    dto.setPhone(customer.getPhone());
    dto.setEmail(customer.getEmail());
    dto.setCompanyName(customer.getCompanyName());
    dto.setDemand(customer.getDemand());
    dto.setStatus(customer.getCustomerStatus() != null ? customer.getCustomerStatus().name() : null);
    dto.setNote(customer.getNote());
    return dto;
  }

  public CustomerDetailResponseDTO toCustomerDetailResponseDTO(CustomerEntity customer) {
    CustomerDetailResponseDTO dto = new CustomerDetailResponseDTO();
    dto.setId(customer.getId());
    dto.setFullName(customer.getFullname());
    dto.setPhone(customer.getPhone());
    dto.setEmail(customer.getEmail());
    dto.setCompanyName(customer.getCompanyName());
    dto.setDemand(customer.getDemand());
    dto.setStatus(customer.getCustomerStatus() != null ? customer.getCustomerStatus().name() : null);
    dto.setNote(customer.getNote());
    return dto;
  }
}