package com.javaweb.api.client;

import com.javaweb.model.client.request.contact.ClientContactRequestCreateDTO;
import com.javaweb.model.client.response.contact.ClientContactRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.client.ClientContactRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/contact-requests")
public class ClientContactRequestAPI {

  @Autowired
  private ClientContactRequestService clientContactRequestService;

  @PostMapping
  public ResponseDTO<?> create(@RequestBody ClientContactRequestCreateDTO request) {
    ClientContactRequestDTO data = clientContactRequestService.create(request);

    ResponseDTO<ClientContactRequestDTO> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Gửi yêu cầu liên hệ thành công");
    response.setData(data);

    return response;
  }
}