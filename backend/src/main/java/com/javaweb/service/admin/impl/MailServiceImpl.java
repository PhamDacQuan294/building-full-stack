package com.javaweb.service.admin.impl;

import com.javaweb.converter.MailContentConverter;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.request.notification.AssignmentMailRequestDTO;
import com.javaweb.model.request.notification.NewUserMailRequestDTO;
import com.javaweb.model.request.notification.ResetPasswordMailRequestDTO;
import com.javaweb.repository.admin.UserRepository;
import com.javaweb.service.admin.EmailLogService;
import com.javaweb.service.admin.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private MailContentConverter mailContentConverter;

  @Autowired
  private EmailLogService emailLogService;

  @Autowired
  private UserRepository userRepository;

  @Value("${app.mail.from}")
  private String fromEmail;

  @Override
  public void sendNewUserMail(NewUserMailRequestDTO request) {
    String subject = "Tài khoản mới đã được tạo";
    String htmlContent = mailContentConverter.buildNewUserMail(request);

    UserEntity actor = findUserById(request.getActorId());
    UserEntity receiver = findUserById(request.getReceiverId());

    sendMail(
      actor,
      receiver,
      request.getToEmail(),
      subject,
      htmlContent,
      "NEW_USER",
      "USER",
      request.getReceiverId()
    );
  }

  @Override
  public void sendResetPasswordOtpMail(ResetPasswordMailRequestDTO request) {
    String subject = "Mã OTP đặt lại mật khẩu";
    String htmlContent = mailContentConverter.buildResetPasswordOtpMail(request);

    UserEntity actor = findUserById(request.getActorId());
    UserEntity receiver = findUserById(request.getReceiverId());

    sendMail(
      actor,
      receiver,
      request.getToEmail(),
      subject,
      htmlContent,
      "RESET_PASSWORD",
      "AUTH",
      request.getReceiverId()
    );
  }

  @Override
  public void sendAssignmentMail(AssignmentMailRequestDTO request) {
    String subject = request.getTitle();
    String htmlContent = mailContentConverter.buildAssignmentMail(request);

    UserEntity actor = findUserById(request.getActorId());
    UserEntity receiver = findUserById(request.getReceiverId());

    sendMail(
      actor,
      receiver,
      request.getToEmail(),
      subject,
      htmlContent,
      "ASSIGNMENT",
      request.getModule(),
      request.getObjectId()
    );
  }

  private void sendMail(
    UserEntity actor,
    UserEntity receiver,
    String toEmail,
    String subject,
    String htmlContent,
    String mailType,
    String module,
    Long objectId
  ) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      helper.setFrom(fromEmail);
      helper.setTo(toEmail);
      helper.setSubject(subject);
      helper.setText(htmlContent, true);

      mailSender.send(mimeMessage);

      emailLogService.save(
        actor,
        receiver,
        toEmail,
        subject,
        htmlContent,
        mailType,
        module,
        objectId,
        true,
        null
      );
    } catch (Exception e) {
      emailLogService.save(
        actor,
        receiver,
        toEmail,
        subject,
        htmlContent,
        mailType,
        module,
        objectId,
        false,
        e.getMessage()
      );
      throw new RuntimeException("Gửi email thất bại: " + e.getMessage());
    }
  }

  private UserEntity findUserById(Long id) {
    if (id == null) return null;
    return userRepository.findById(id).orElse(null);
  }
}