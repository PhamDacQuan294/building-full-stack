package com.javaweb.service.admin;

import com.javaweb.model.request.profile.ChangePasswordRequestDTO;
import com.javaweb.model.request.profile.UpdateProfileRequestDTO;
import com.javaweb.model.response.profile.ProfileResponseDTO;

public interface ProfileService {
  ProfileResponseDTO getMyProfile();
  void updateMyProfile(UpdateProfileRequestDTO request);
  void changePassword(ChangePasswordRequestDTO request);
}