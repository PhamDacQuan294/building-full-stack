package com.javaweb.api.admin;

import com.javaweb.model.request.auth.LoginRequestDTO;
import com.javaweb.service.admin.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserAPI {
  @Autowired
  private IUserService userService;

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
