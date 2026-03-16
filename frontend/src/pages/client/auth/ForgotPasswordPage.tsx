import { useState } from "react";
import { useClientAuthStore } from "@/stores/client/useClientAuthStore";

export default function ForgotPasswordPage() {
  const { forgotPassword, resetPassword, loading, successMessage, errorMessage, clearMessages } =
    useClientAuthStore();

  const [step, setStep] = useState(1);
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");

  const handleSendOtp = async (e: React.FormEvent) => {
    e.preventDefault();
    clearMessages();

    const ok = await forgotPassword({ email });
    if (ok) {
      setStep(2);
    }
  };

  const handleResetPassword = async (e: React.FormEvent) => {
    e.preventDefault();
    clearMessages();

    await resetPassword({
      email,
      otp,
      newPassword,
    });
  };

  return (
    <div className="flex items-center justify-center min-h-screen px-4 bg-slate-50">
      <div className="w-full max-w-md p-8 bg-white shadow-sm rounded-3xl">
        <h1 className="text-2xl font-bold text-slate-900">Quên mật khẩu</h1>

        {step === 1 ? (
          <form onSubmit={handleSendOtp} className="mt-6 space-y-4">
            <input
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Nhập email"
              className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
            />

            {successMessage && (
              <div className="px-4 py-3 text-sm text-green-700 rounded-2xl bg-green-50">
                {successMessage}
              </div>
            )}

            {errorMessage && (
              <div className="px-4 py-3 text-sm text-red-700 rounded-2xl bg-red-50">
                {errorMessage}
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full px-4 py-3 font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700"
            >
              {loading ? "Đang gửi..." : "Gửi OTP"}
            </button>
          </form>
        ) : (
          <form onSubmit={handleResetPassword} className="mt-6 space-y-4">
            <input
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              placeholder="Nhập OTP"
              className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
            />

            <input
              type="password"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              placeholder="Nhập mật khẩu mới"
              className="w-full px-4 py-3 border outline-none rounded-2xl border-slate-300 focus:border-violet-500"
            />

            {successMessage && (
              <div className="px-4 py-3 text-sm text-green-700 rounded-2xl bg-green-50">
                {successMessage}
              </div>
            )}

            {errorMessage && (
              <div className="px-4 py-3 text-sm text-red-700 rounded-2xl bg-red-50">
                {errorMessage}
              </div>
            )}

            <button
              type="submit"
              disabled={loading}
              className="w-full px-4 py-3 font-medium text-white rounded-2xl bg-violet-600 hover:bg-violet-700"
            >
              {loading ? "Đang xử lý..." : "Đặt lại mật khẩu"}
            </button>
          </form>
        )}
      </div>
    </div>
  );
}