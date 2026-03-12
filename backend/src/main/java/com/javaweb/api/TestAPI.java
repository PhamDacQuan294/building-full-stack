package com.javaweb.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestAPI {

  @GetMapping("/encode")
  public String encode(@RequestParam String password){
    return new BCryptPasswordEncoder().encode(password);
  }
}