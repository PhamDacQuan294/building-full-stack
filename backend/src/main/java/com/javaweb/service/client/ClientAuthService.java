package com.javaweb.service.client;

import com.javaweb.model.client.request.auth.*;
import com.javaweb.model.client.response.auth.ClientLoginResponseDTO;
import com.javaweb.model.client.response.auth.ClientProfileDTO;

public interface ClientAuthService {
  ClientProfileDTO register(ClientRegisterRequestDTO request);
  ClientLoginResponseDTO login(ClientLoginRequestDTO request);
  ClientProfileDTO getMyProfile(String email);
  ClientProfileDTO updateProfile(String email, ClientUpdateProfileRequestDTO request);
  void changePassword(String email, ClientChangePasswordRequestDTO request);
  void forgotPassword(ClientForgotPasswordRequestDTO request);
  void resetPassword(ClientResetPasswordRequestDTO request);
}