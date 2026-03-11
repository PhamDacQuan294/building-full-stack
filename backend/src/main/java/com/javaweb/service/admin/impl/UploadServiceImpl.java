package com.javaweb.service.admin.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.javaweb.service.admin.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {
  @Autowired
  private Cloudinary cloudinary;

  @Override
  public String uploadFile(MultipartFile file) {
    try {
      Map uploadResult = cloudinary.uploader().upload(
        file.getBytes(),
        ObjectUtils.asMap("folder", "buildings")
      );

      return uploadResult.get("secure_url").toString();
    } catch (Exception e) {
      throw new RuntimeException("Upload ảnh thất bại");
    }
  }
}
