package com.javaweb.service.admin;

import java.util.Map;

public interface IUserService {
  Map<Long, String> getStaffs();

  String login(String email, String password) throws Exception;
}
