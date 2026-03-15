package com.javaweb.api.admin;

import com.javaweb.model.request.customer.AssignmentCustomerRequestDTO;
import com.javaweb.model.request.customer.CustomerCareRequestDTO;
import com.javaweb.model.request.customer.CustomerRequestDTO;
import com.javaweb.model.request.customer.CustomerSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.customer.CustomerCareResponseDTO;
import com.javaweb.model.response.customer.CustomerDetailResponseDTO;
import com.javaweb.model.response.customer.CustomerResponseDTO;
import com.javaweb.model.response.customer.StaffAssignmentResponseDTO;
import com.javaweb.service.admin.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/customers")
public class CustomerAPI {

  @Autowired
  private CustomerService customerService;

  @GetMapping
  public ResponseDTO<?> getCustomers(CustomerSearchRequestDTO request) {
    List<CustomerResponseDTO> customers = customerService.findAll(request);
    int totalItems = customerService.countTotalItems(request);

    Map<String, Object> result = new HashMap<>();
    result.put("customers", customers);
    result.put("totalItems", totalItems);

    ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
    response.setData(result);
    response.setMessage("success");
    response.setDetail("Lấy danh sách khách hàng thành công");

    return response;
  }

  @PostMapping("/create")
  public ResponseDTO<?> createCustomer(@RequestBody CustomerRequestDTO request) {
    customerService.createCustomer(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Thêm khách hàng thành công");
    return response;
  }

  @PutMapping("/edit/{id}")
  public ResponseDTO<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDTO request) {
    customerService.updateCustomer(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật khách hàng thành công");
    return response;
  }

  @DeleteMapping("/delete/{id}")
  public ResponseDTO<?> deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Xóa khách hàng thành công");
    return response;
  }

  @GetMapping("/{id}")
  public ResponseDTO<?> getCustomerDetail(@PathVariable Long id) {
    CustomerDetailResponseDTO customer = customerService.getCustomerDetail(id);

    ResponseDTO<CustomerDetailResponseDTO> response = new ResponseDTO<>();
    response.setData(customer);
    response.setMessage("success");
    response.setDetail("Lấy chi tiết khách hàng thành công");
    return response;
  }

//  @GetMapping("/{id}/staffs")
//  public ResponseDTO<?> getStaffs(@PathVariable Long id) {
//    return customerService.listStaffs(id);
//  }

  @PostMapping("/assignment")
  public ResponseDTO<?> assignmentCustomer(@RequestBody AssignmentCustomerRequestDTO request) {
    customerService.updateAssignmentCustomer(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Giao khách hàng thành công");
    return response;
  }

  @GetMapping("/{id}/staffs")
  public ResponseDTO<?> getCustomerStaffs(@PathVariable Long id) {
    List<StaffAssignmentResponseDTO> staffs = customerService.getStaffAssignments(id);

    ResponseDTO<List<StaffAssignmentResponseDTO>> response = new ResponseDTO<>();
    response.setData(staffs);
    response.setMessage("success");
    response.setDetail("Lấy danh sách staff thành công");
    return response;
  }

  @GetMapping("/{id}/care-history")
  public ResponseDTO<?> getCareHistory(@PathVariable Long id) {
    List<CustomerCareResponseDTO> items = customerService.getCareHistory(id);

    ResponseDTO<List<CustomerCareResponseDTO>> response = new ResponseDTO<>();
    response.setData(items);
    response.setMessage("success");
    response.setDetail("Lấy lịch sử chăm sóc thành công");
    return response;
  }

  @PostMapping("/care-history")
  public ResponseDTO<?> createCareHistory(@RequestBody CustomerCareRequestDTO request) {
    customerService.createCareHistory(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Thêm lịch sử chăm sóc thành công");
    return response;
  }
}