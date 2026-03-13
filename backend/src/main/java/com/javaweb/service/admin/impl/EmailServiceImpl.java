package com.javaweb.service.admin.impl;

import com.javaweb.service.admin.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Override
  public void sendOtpEmail(String toEmail, String otp) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

      String html = """
                <div style="font-family: Arial, sans-serif; background:#f8fafc; padding:24px;">
                  <div style="max-width:520px; margin:0 auto; background:white; border-radius:16px; padding:32px; border:1px solid #e2e8f0;">
                    <h2 style="margin:0 0 16px; color:#111827; text-align:center;">Xác nhận quên mật khẩu</h2>
                    <p style="font-size:15px; color:#475569; text-align:center; margin-bottom:24px;">
                      Sử dụng mã OTP bên dưới để tiếp tục đặt lại mật khẩu.
                    </p>

                    <div style="text-align:center; margin:24px 0;">
                      <span style="
                        display:inline-block;
                        font-size:32px;
                        letter-spacing:10px;
                        font-weight:700;
                        color:#7c3aed;
                        background:#f5f3ff;
                        padding:18px 28px;
                        border-radius:14px;
                        border:1px dashed #c4b5fd;
                      ">
                        %s
                      </span>
                    </div>

                    <p style="font-size:14px; color:#ef4444; text-align:center; margin-top:16px;">
                      OTP có hiệu lực trong 3 phút.
                    </p>

                    <p style="font-size:13px; color:#94a3b8; text-align:center; margin-top:24px;">
                      Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.
                    </p>
                  </div>
                </div>
                """.formatted(otp);

      helper.setTo(toEmail);
      helper.setSubject("Mã OTP đặt lại mật khẩu");
      helper.setText(html, true);

      mailSender.send(message);
    } catch (Exception e) {
      throw new RuntimeException("Không gửi được email OTP");
    }
  }
}