package com.javaweb.service.client;

import com.javaweb.model.client.request.contact.ClientContactRequestCreateDTO;
import com.javaweb.model.client.response.contact.ClientContactRequestDTO;

public interface ClientContactRequestService {
  ClientContactRequestDTO create(ClientContactRequestCreateDTO request);
}