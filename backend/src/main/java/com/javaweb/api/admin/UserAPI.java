package com.javaweb.api.admin;

import com.javaweb.model.request.auth.LoginRequestDTO;
import com.javaweb.model.request.user.CreateUserRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.user.UserItemResponseDTO;
import com.javaweb.service.admin.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserAPI {
  @Autowired
  private IUserService userService;

  @GetMapping("")
  public ResponseEntity<ResponseDTO<List<UserItemResponseDTO>>> getUsers() {
    try {
      List<UserItemResponseDTO> users = userService.getUsers();

      ResponseDTO<List<UserItemResponseDTO>> response = new ResponseDTO<>();
      response.setData(users);
      response.setMessage("success");
      response.setDetail("Lấy danh sách người dùng thành công");

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      ResponseDTO<List<UserItemResponseDTO>> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail(e.getMessage());

      return ResponseEntity.badRequest().body(response);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<ResponseDTO<?>> createUser(@RequestBody CreateUserRequestDTO request) {
    try {
      userService.createUser(request);

      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("success");
      response.setDetail("Thêm người dùng thành công");

      return ResponseEntity.ok(response);
    } catch (RuntimeException e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail(e.getMessage());

      return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
      ResponseDTO<Object> response = new ResponseDTO<>();
      response.setData(null);
      response.setMessage("failed");
      response.setDetail("Lỗi server");

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto, BindingResult result) {
    try {
      if (result.hasErrors()) {
        List<String> errors = result.getFieldErrors()
          .stream()
          .map(fieldError -> fieldError.getDefaultMessage())
          .toList();

        return ResponseEntity.badRequest().body(errors);
      }

      String token = userService.login(dto.getEmail(), dto.getPassword());
      return ResponseEntity.ok(token);
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
  }
}
