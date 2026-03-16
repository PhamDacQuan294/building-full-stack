package com.javaweb.api.admin;

import com.javaweb.model.request.user.ChangeUserStatusRequestDTO;
import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.request.user.ResetUserPasswordRequestDTO;
import com.javaweb.model.request.user.UpdateUserRequestDTO;
import com.javaweb.model.request.user.UserSearchRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.user.UserDetailResponseDTO;
import com.javaweb.model.response.user.UserResponseDTO;
import com.javaweb.service.admin.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users2")
public class UserManagementAPI {

  @Autowired
  private UserManagementService userManagementService;

  @GetMapping
  public ResponseDTO<?> getUsers(UserSearchRequestDTO request) {
    List<UserResponseDTO> users = userManagementService.findAll(request);
    long totalItems = userManagementService.countTotalItems(request);

    ResponseDTO<List<UserResponseDTO>> response = new ResponseDTO<>();
    response.setData(users);
    response.setTotalItem(totalItems);
    response.setMessage("success");
    response.setDetail("Lấy danh sách user thành công");

    return response;
  }

  @GetMapping("/{id}")
  public ResponseDTO<?> getUserDetail(@PathVariable Long id) {
    UserDetailResponseDTO data = userManagementService.getDetail(id);

    ResponseDTO<UserDetailResponseDTO> response = new ResponseDTO<>();
    response.setData(data);
    response.setMessage("success");
    response.setDetail("Lấy chi tiết user thành công");

    return response;
  }

  @PostMapping("/create")
  public ResponseDTO<?> createUser(@RequestBody CreateUserRequestDTO request) {
    userManagementService.createUser(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Tạo user thành công");

    return response;
  }

  @PutMapping("/edit/{id}")
  public ResponseDTO<?> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequestDTO request) {
    userManagementService.updateUser(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Cập nhật user thành công");

    return response;
  }

  @PutMapping("/{id}/status")
  public ResponseDTO<?> changeStatus(@PathVariable Long id, @RequestBody ChangeUserStatusRequestDTO request) {
    userManagementService.changeStatus(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Đổi trạng thái user thành công");

    return response;
  }

  @PutMapping("/{id}/reset-password")
  public ResponseDTO<?> resetPassword(@PathVariable Long id, @RequestBody ResetUserPasswordRequestDTO request) {
    userManagementService.resetPassword(id, request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Reset mật khẩu thành công");

    return response;
  }
}