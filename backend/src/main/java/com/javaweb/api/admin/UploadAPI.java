package com.javaweb.api.admin;

import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.admin.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/upload")
public class UploadAPI {

  @Autowired
  private UploadService uploadService;

  @PostMapping("/image")
  public ResponseDTO<?> uploadImage(@RequestParam("file") MultipartFile file) {
    String imageUrl = uploadService.uploadFile(file);

    ResponseDTO<String> response = new ResponseDTO<>();
    response.setData(imageUrl);
    response.setMessage("success");
    response.setDetail("Upload ảnh thành công");

    return response;
  }
}