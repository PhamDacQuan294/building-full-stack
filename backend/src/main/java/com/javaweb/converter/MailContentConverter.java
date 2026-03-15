package com.javaweb.converter;

import com.javaweb.model.request.notification.AssignmentMailRequestDTO;
import com.javaweb.model.request.notification.NewUserMailRequestDTO;
import com.javaweb.model.request.notification.ResetPasswordMailRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailContentConverter {

  @Value("${app.frontend.url}")
  private String frontendUrl;

  public String buildNewUserMail(NewUserMailRequestDTO request) {
    return """
            <div style="font-family:Arial,sans-serif;line-height:1.6">
                <h2>Tài khoản mới đã được tạo</h2>
                <p>Xin chào <b>%s</b>,</p>
                <p>Admin đã tạo tài khoản mới cho bạn.</p>
                <p><b>Email:</b> %s</p>
                <p><b>Mật khẩu:</b> %s</p>
                <p><b>Nhóm quyền:</b> %s</p>
                <p>Đăng nhập tại: <a href="%s/admin/login">%s/admin/login</a></p>
                <p>Vui lòng đổi mật khẩu sau khi đăng nhập.</p>
            </div>
            """.formatted(
      request.getFullName(),
      request.getEmail(),
      request.getPassword(),
      request.getRoleName(),
      frontendUrl,
      frontendUrl
    );
  }

  public String buildResetPasswordOtpMail(ResetPasswordMailRequestDTO request) {
    return """
            <div style="font-family:Arial,sans-serif;line-height:1.6">
                <h2>Mã OTP đặt lại mật khẩu</h2>
                <p>Xin chào <b>%s</b>,</p>
                <p>Mã OTP của bạn là:</p>
                <div style="font-size:28px;font-weight:bold;color:#7c3aed;letter-spacing:6px">%s</div>
                <p>Mã có hiệu lực trong 3 phút.</p>
                <p>Nếu bạn không yêu cầu thao tác này, vui lòng bỏ qua email.</p>
            </div>
            """.formatted(
      request.getFullName(),
      request.getOtp()
    );
  }

  public String buildAssignmentMail(AssignmentMailRequestDTO request) {
    return """
            <div style="font-family:Arial,sans-serif;line-height:1.6">
                <h2>%s</h2>
                <p>Xin chào <b>%s</b>,</p>
                <p>%s</p>
                <p>Vui lòng đăng nhập hệ thống để xem chi tiết.</p>
                <p><a href="%s/admin/login">%s/admin/login</a></p>
            </div>
            """.formatted(
      request.getTitle(),
      request.getStaffName(),
      request.getContent(),
      frontendUrl,
      frontendUrl
    );
  }
}