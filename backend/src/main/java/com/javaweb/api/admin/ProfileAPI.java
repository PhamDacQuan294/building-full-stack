package com.javaweb.api.admin;

import com.javaweb.model.request.profile.ChangePasswordRequestDTO;
import com.javaweb.model.request.profile.UpdateProfileRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.profile.ProfileResponseDTO;
import com.javaweb.service.admin.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/profile")
public class ProfileAPI {

  @Autowired
  private ProfileService profileService;

  @GetMapping
  public ResponseDTO<?> getMyProfile() {
    ProfileResponseDTO data = profileService.getMyProfile();

    ResponseDTO<ProfileResponseDTO> response = new ResponseDTO<>();
    response.setData(data);
    response.setMessage("success");
    response.setDetail("Lấy thông tin cá nhân thành công");
    return response;
  }

  @PutMapping
  public ResponseDTO<?> updateMyProfile(@RequestBody UpdateProfileRequestDTO request) {
    profileService.updateMyProfile(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật thông tin cá nhân thành công");
    return response;
  }

  @PutMapping("/change-password")
  public ResponseDTO<?> changePassword(@RequestBody ChangePasswordRequestDTO request) {
    profileService.changePassword(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đổi mật khẩu thành công");
    return response;
  }
}