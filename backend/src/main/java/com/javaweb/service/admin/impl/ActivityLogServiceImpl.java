package com.javaweb.service.admin.impl;

import com.javaweb.entity.ActivityLogEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.activitylog.ActivityLogSearchRequestDTO;
import com.javaweb.model.response.activitylog.ActivityLogResponseDTO;
import com.javaweb.repository.admin.ActivityLogRepository;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

  @Autowired
  private ActivityLogRepository activityLogRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void save(String action, String module, String description, Long objectId) {
    UserEntity currentUser = getCurrentUser();

    ActivityLogEntity log = new ActivityLogEntity();
    log.setActor(currentUser);
    log.setActorEmail(currentUser.getEmail());
    log.setActorName(currentUser.getFullname());
    log.setAction(action);
    log.setModule(module);
    log.setDescription(description);
    log.setObjectId(objectId);
    log.setCreatedDate(new Date());

    activityLogRepository.save(log);
  }

  @Override
  public List<ActivityLogResponseDTO> search(ActivityLogSearchRequestDTO request) {
    return activityLogRepository.search(request);
  }

  @Override
  public long count(ActivityLogSearchRequestDTO request) {
    return activityLogRepository.count(request);
  }

  private UserEntity getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new RuntimeException("Không tìm thấy user đang đăng nhập");
    }

    String email = authentication.getName();

    return userRepository.findByEmail(email)
      .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
  }
}