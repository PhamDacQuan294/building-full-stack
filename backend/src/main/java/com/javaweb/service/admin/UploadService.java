package com.javaweb.service.admin;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
  String uploadFile(MultipartFile file);
}
