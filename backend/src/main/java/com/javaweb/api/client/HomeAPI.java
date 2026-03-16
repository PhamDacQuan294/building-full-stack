package com.javaweb.api.client;

import com.javaweb.model.client.request.home.HomeSearchRequestDTO;
import com.javaweb.model.client.response.home.HomePageResponseDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.client.ClientHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/client/home")
public class HomeAPI {

  @Autowired
  private ClientHomeService clientHomeService;

  @GetMapping
  public ResponseDTO<?> getHomePageData() {
    HomePageResponseDTO data = clientHomeService.getHomePageData();

    ResponseDTO<HomePageResponseDTO> response = new ResponseDTO<>();
    response.setData(data);
    response.setMessage("success");
    response.setDetail("Lấy dữ liệu trang chủ thành công");

    return response;
  }

  @GetMapping("/search")
  public ResponseDTO<?> searchBuildings(HomeSearchRequestDTO request) {
    Map<String, Object> data = clientHomeService.searchBuildings(request);

    ResponseDTO<Map<String, Object>> response = new ResponseDTO<>();
    response.setData(data);
    response.setMessage("success");
    response.setDetail("Tìm kiếm bất động sản thành công");

    return response;
  }
}