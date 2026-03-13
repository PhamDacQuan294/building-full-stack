import { authService } from "@/services/admin/authService";
import { useEffect, useMemo, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { toast } from "sonner";

const OTP_LENGTH = 6;
const EXPIRE_SECONDS = 180;

export default function VerifyOtp() {
  const navigate = useNavigate();
  const location = useLocation();

  const email = new URLSearchParams(location.search).get("email") || "";

  const [otpList, setOtpList] = useState<string[]>(["", "", "", "", "", ""]);
  const [secondsLeft, setSecondsLeft] = useState(EXPIRE_SECONDS);
  const [loading, setLoading] = useState(false);
  const [resendLoading, setResendLoading] = useState(false);

  const inputRefs = useRef<Array<HTMLInputElement | null>>([]);

  const maskedEmail = useMemo(() => hideEmail(email), [email]);
  const otpValue = otpList.join("");

  useEffect(() => {
    if (secondsLeft <= 0) return;

    const timer = setInterval(() => {
      setSecondsLeft((prev) => prev - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, [secondsLeft]);

  const handleChangeOtp = (index: number, value: string) => {
    const onlyNumber = value.replace(/\D/g, "");

    const newOtpList = [...otpList];

    if (!onlyNumber) {
      newOtpList[index] = "";
      setOtpList(newOtpList);
      return;
    }

    newOtpList[index] = onlyNumber.slice(-1);
    setOtpList(newOtpList);

    if (index < OTP_LENGTH - 1) {
      inputRefs.current[index + 1]?.focus();
    }
  };

  const handleKeyDownOtp = (
    index: number,
    event: React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (event.key === "Backspace" && otpList[index] === "" && index > 0) {
      inputRefs.current[index - 1]?.focus();
    }
  };

  const handlePasteOtp = (event: React.ClipboardEvent<HTMLInputElement>) => {
    event.preventDefault();

    const pastedText = event.clipboardData.getData("text");
    const onlyNumber = pastedText.replace(/\D/g, "").slice(0, OTP_LENGTH);

    if (!onlyNumber) return;

    const newOtpList = ["", "", "", "", "", ""];

    for (let i = 0; i < onlyNumber.length; i++) {
      newOtpList[i] = onlyNumber[i];
    }

    setOtpList(newOtpList);

    const focusIndex =
      onlyNumber.length >= OTP_LENGTH ? OTP_LENGTH - 1 : onlyNumber.length;

    inputRefs.current[focusIndex]?.focus();
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (otpValue.length !== OTP_LENGTH) {
      toast.error("Vui lòng nhập đủ 6 số OTP");
      return;
    }

    try {
      setLoading(true);

      await authService.verifyOtp(email, otpValue);

      toast.success("OTP hợp lệ");
      navigate(
        `/reset-password?email=${encodeURIComponent(email)}&otp=${otpValue}`
      );
    } catch (error: any) {
      console.error("VERIFY OTP ERROR:", error?.response?.data || error);
      toast.error(error?.response?.data?.detail || "OTP không hợp lệ");
    } finally {
      setLoading(false);
    }
  };

  const handleResendOtp = async () => {
    try {
      setResendLoading(true);

      await authService.forgotPassword(email);

      setOtpList(["", "", "", "", "", ""]);
      setSecondsLeft(EXPIRE_SECONDS);

      inputRefs.current[0]?.focus();

      toast.success("Đã gửi lại OTP");
    } catch (error: any) {
      console.error("RESEND OTP ERROR:", error?.response?.data || error);
      toast.error(error?.response?.data?.detail || "Gửi lại OTP thất bại");
    } finally {
      setResendLoading(false);
    }
  };

  const handleBackToForgotPassword = () => {
    navigate("/forgot-password");
  };

  return (
    <div className="flex items-center justify-center min-h-screen p-4 bg-slate-100">
      <div className="w-full max-w-md p-6 bg-white shadow rounded-2xl">
        <h1 className="mb-2 text-2xl font-bold text-center">Xác nhận OTP</h1>

        <p className="mb-6 text-sm text-center text-slate-500">
          Mã OTP đã được gửi tới{" "}
          <span className="font-medium text-slate-700">{maskedEmail}</span>
        </p>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div className="flex justify-center gap-3">
            {otpList.map((item, index) => (
              <input
                key={index}
                ref={(element) => {
                  inputRefs.current[index] = element;
                }}
                value={item}
                onChange={(e) => handleChangeOtp(index, e.target.value)}
                onKeyDown={(e) => handleKeyDownOtp(index, e)}
                onPaste={handlePasteOtp}
                inputMode="numeric"
                maxLength={1}
                className="w-12 h-12 text-lg font-semibold text-center border outline-none rounded-xl focus:ring-2 focus:ring-violet-500"
              />
            ))}
          </div>

          <div className="text-sm text-center text-slate-500">
            {secondsLeft > 0 ? (
              <span>
                OTP hết hạn sau:{" "}
                <span className="font-semibold text-violet-600">
                  {formatTime(secondsLeft)}
                </span>
              </span>
            ) : (
              <span className="font-medium text-red-500">OTP đã hết hạn</span>
            )}
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 text-white rounded-xl bg-violet-600"
          >
            {loading ? "Đang xác nhận..." : "Xác nhận OTP"}
          </button>
        </form>

        <div className="mt-4 space-y-2 text-center">
          <button
            type="button"
            onClick={handleResendOtp}
            disabled={secondsLeft > 0 || resendLoading}
            className="text-sm font-medium text-violet-600 disabled:text-slate-400"
          >
            {resendLoading ? "Đang gửi lại..." : "Gửi lại OTP"}
          </button>

          <div>
            <button
              type="button"
              onClick={handleBackToForgotPassword}
              className="text-sm text-slate-500 hover:text-slate-700"
            >
              Quay lại nhập email khác
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

function formatTime(totalSeconds: number) {
  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;

  return `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
}

function hideEmail(email: string) {
  if (!email.includes("@")) return email;

  const [name, domain] = email.split("@");

  if (name.length <= 3) {
    return `${name[0] || ""}***@${domain}`;
  }

  return `${name.slice(0, 3)}***@${domain}`;
}