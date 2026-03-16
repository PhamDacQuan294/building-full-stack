package com.javaweb.service.admin;

import com.javaweb.model.request.contact.AdminContactRequestUpdateStatusDTO;
import com.javaweb.model.response.contact.AdminContactRequestListItemDTO;

import java.util.List;

public interface AdminContactRequestService {
  List<AdminContactRequestListItemDTO> findAll();
  AdminContactRequestListItemDTO findById(Long id);
  void updateStatus(Long id, AdminContactRequestUpdateStatusDTO request);
}