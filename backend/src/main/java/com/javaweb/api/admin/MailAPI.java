package com.javaweb.api.admin;

import com.javaweb.model.request.notification.AssignmentMailRequestDTO;
import com.javaweb.model.request.notification.EmailLogSearchRequestDTO;
import com.javaweb.model.request.notification.NewUserMailRequestDTO;
import com.javaweb.model.request.notification.ResetPasswordMailRequestDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.notification.EmailLogResponseDTO;
import com.javaweb.service.admin.EmailLogService;
import com.javaweb.service.admin.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/mails")
public class MailAPI {

  @Autowired
  private MailService mailService;

  @Autowired
  private EmailLogService emailLogService;

  @PostMapping("/new-user")
  public ResponseDTO<?> sendNewUserMail(@RequestBody NewUserMailRequestDTO request) {
    mailService.sendNewUserMail(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Gửi mail tài khoản mới thành công");
    return response;
  }

  @PostMapping("/reset-password")
  public ResponseDTO<?> sendResetPasswordMail(@RequestBody ResetPasswordMailRequestDTO request) {
    mailService.sendResetPasswordOtpMail(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Gửi mail OTP thành công");
    return response;
  }

  @PostMapping("/assignment")
  public ResponseDTO<?> sendAssignmentMail(@RequestBody AssignmentMailRequestDTO request) {
    mailService.sendAssignmentMail(request);

    ResponseDTO<Object> response = new ResponseDTO<>();
    response.setMessage("success");
    response.setDetail("Gửi mail giao việc thành công");
    return response;
  }

  @GetMapping("/logs")
  public ResponseDTO<?> getEmailLogs(EmailLogSearchRequestDTO request) {
    List<EmailLogResponseDTO> items = emailLogService.search(request);
    long totalItems = emailLogService.count(request);

    ResponseDTO<List<EmailLogResponseDTO>> response = new ResponseDTO<>();
    response.setData(items);
    response.setTotalItem(totalItems);
    response.setMessage("success");
    response.setDetail("Lấy danh sách email log thành công");

    return response;
  }
}